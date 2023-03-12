/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.deob.deobfuscators;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.runelite.asm.ClassFile;
import net.runelite.asm.ClassGroup;
import net.runelite.asm.Method;
import net.runelite.asm.attributes.Code;
import net.runelite.asm.attributes.code.Instruction;
import net.runelite.asm.attributes.code.instruction.types.InvokeInstruction;
import net.runelite.asm.attributes.code.instruction.types.LVTInstruction;
import net.runelite.asm.execution.Execution;
import net.runelite.asm.execution.InstructionContext;
import net.runelite.asm.execution.StackContext;
import net.runelite.asm.signature.Signature;
import net.runelite.asm.signature.util.VirtualMethods;
import net.runelite.deob.Deob;
import net.runelite.deob.DeobAnnotations;
import net.runelite.deob.Deobfuscator;
public class UnusedParameters implements Deobfuscator
{
	private final Map<List<Method>, Collection<Integer>> unused = new HashMap<>();
	private final Multimap<Instruction, InstructionContext> invokes = HashMultimap.create();

	private void visit(InstructionContext ictx)
	{
		Instruction i = ictx.getInstruction();

		if (!(i instanceof InvokeInstruction))
		{
			return;
		}

		invokes.put(i, ictx);
	}

	private void buildUnused(ClassGroup group)
	{
		unused.clear();

		for (ClassFile cf : group.getClasses())
		{
			for (Method m : cf.getMethods())
			{
				if (!Deob.isObfuscated(m.getName()))
				{
					continue;
				}

				List<Method> ms = VirtualMethods.getVirtualMethods(m);
				Collection<Integer> u = this.findUnusedParameters(ms);
				if (!u.isEmpty())
				{
					unused.put(ms, u);
				}
			}
		}
	}

	private boolean shouldRemove(Method m, int parameter)
	{
		final net.runelite.asm.Annotation a = m.findAnnotation(DeobAnnotations.OBFUSCATED_SIGNATURE);
		if (a == null)
			return false;
		final Object str = a.get("descriptor");

		return parameter + 1 == new Signature((String) str).size();
	}

	private int processUnused(Execution execution, ClassGroup group)
	{
		int count = 0;

		for (Map.Entry<List<Method>, Collection<Integer>> entry : unused.entrySet())
		{
			List<Method> m = entry.getKey();
			Collection<Integer> u = entry.getValue();

			int offset = m.size() == 1 && m.get(0).isStatic() ? 0 : 1;

			for (int unusedParameter : u)
			{
				if (!shouldRemove(m.get(0), unusedParameter))
				{
					continue;
				}

				Signature descriptor = m.get(0).getDescriptor();
				int lvtIndex = this.getLvtIndex(descriptor, offset, unusedParameter);
				/* removing the parameter can't cause collisions on other (overloaded) methods because prior to this we rename
				 * all classes/fields/methods to have unique names.
				 */
				removeParameter(group, m, descriptor, execution, unusedParameter, lvtIndex);
				break;
			}

			++count;
		}

		return count;
	}

	private Set<Integer> findUnusedParameters(Method method)
	{
		int offset = method.isStatic() ? 0 : 1;
		Signature descriptor = method.getDescriptor();
		List<Integer> unusedParams = new ArrayList<>();

		for (int variableIndex = 0, lvtIndex = offset;
			variableIndex < descriptor.size();
			lvtIndex += descriptor.getTypeOfArg(variableIndex++).getSize())
		{
			List<? extends Instruction> lv = method.findLVTInstructionsForVariable(lvtIndex);
			if (lv == null || lv.isEmpty())
			{
				unusedParams.add(variableIndex);
			}
		}

		return ImmutableSet.copyOf(unusedParams);
	}

	@SuppressWarnings("empty-statement")
	private int getLvtIndex(Signature signature, int offset, int parameter)
	{
		// get lvt index for parameter
		int lvtIndex = offset;
		for (int variableIndex = 0;
			variableIndex < parameter;
			lvtIndex += signature.getTypeOfArg(variableIndex++).getSize());
		return lvtIndex;
	}

	public Collection<Integer> findUnusedParameters(Collection<Method> methods)
	{
		Set<Integer> list = null;

		for (Method m : methods)
		{
			Set<Integer> p = findUnusedParameters(m);

			if (list == null)
			{
				list = p;
			}
			else
			{
				list = Sets.intersection(list, p);
			}
		}

		List<Integer> l = new ArrayList<>(list != null ? list : new ArrayList<>()); // i know
		Collections.sort(l);
		Collections.reverse(l);
		return l;
	}

	public void removeParameter(ClassGroup group, List<Method> methods, Signature signature, Execution execution, int paramIndex, int lvtIndex)
	{
		int slots = signature.getTypeOfArg(paramIndex).getSize();

		for (ClassFile cf : group.getClasses())
		{
			for (Method m : cf.getMethods())
			{
				Code c = m.getCode();

				if (c == null)
				{
					continue;
				}

				for (Instruction i : new ArrayList<>(c.getInstructions().getInstructions()))
				{
					if (!(i instanceof InvokeInstruction))
					{
						continue;
					}

					InvokeInstruction ii = (InvokeInstruction) i;

					if (ii.getMethods().stream().noneMatch(methods::contains))
					{
						continue;
					}

					ii.removeParameter(paramIndex); // remove parameter from instruction

					Collection<InstructionContext> ics = invokes.get(i);
					assert ics != null;
					for (InstructionContext ins : ics)
					{
						int pops = signature.size() - paramIndex - 1; // index from top of stack of parameter. 0 is the last parameter

						StackContext sctx = ins.getPops().get(pops);
						if (sctx.getPushed().getInstruction().getInstructions() == null)
						{
							continue;
						}

						ins.removeStack(pops); // remove parameter from stack
					}
				}
			}
		}

		for (Method method : methods)
		{
			if (method.getCode() != null)
			// adjust lvt indexes to get rid of idx in the method
			{
				for (Instruction ins : method.getCode().getInstructions().getInstructions())
				{
					if (ins instanceof LVTInstruction)
					{
						LVTInstruction lins = (LVTInstruction) ins;

						int i = lins.getVariableIndex();
						assert i != lvtIndex; // current unused variable detection just looks for no accesses

						// reassign
						if (i > lvtIndex)
						{
							assert i > 0;
							assert i >= lvtIndex + slots;

							Instruction newIns = lins.setVariableIndex(i - slots);
							assert ins == newIns;
						}
					}
				}
			}
		}

		for (Method method : methods)
		{
			method.getDescriptor().remove(paramIndex);
		}
	}

	private int count;

	@Override
	public void run(ClassGroup group)
	{
		int i;

		group.buildClassGraph();

		invokes.clear();
		this.buildUnused(group);

		Execution execution = new Execution(group);
		execution.addExecutionVisitor(this::visit);
		execution.populateInitialMethods();
		execution.run();

		i = this.processUnused(execution, group);

		count += i;

	}

	public int getCount()
	{
		return count;
	}
}
