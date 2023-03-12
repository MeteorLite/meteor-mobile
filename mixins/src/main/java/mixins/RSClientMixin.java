/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * Copyright (c) 2020, ThatGamerBlue <thatgamerblue@gmail.com>
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
package mixins;

import net.runelite.api.Client;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.MethodHook;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;

import meteor.eventbus.Events;
import meteor.eventbus.events.GameStateChanged;
import meteor.eventbus.events.LoginIndexChanged;
import meteor.eventbus.events.LoginStateChanged;

@Mixin(RSClient.class)
public abstract class RSClientMixin implements RSClient {

    @Inject
    private Callbacks callbacks;

    @Shadow("client")
    private static Client client;

    @Inject
    @Override
    public Callbacks getCallbacks() {
        return callbacks;
    }

    @Inject
    @MethodHook(value = "setLoginIndex", end = true)
    public static void onLoginIndexChanged(int newValue) {
        client.getCallbacks().post(Events.LOGIN_INDEX_CHANGED, new LoginIndexChanged(newValue));
    }

    @Inject
    @MethodHook(value = "updateGameState", end = true)
    public static void onGameStateChanged(int newValue) {
        client.getCallbacks().post(Events.GAME_STATE_CHANGED, new GameStateChanged(newValue));
    }

    @Inject
    @MethodHook(value = "setLoginState", end = true)
    public static void onLoginStateChanged(int newValue) {
        client.getCallbacks().post(Events.LOGIN_STATE_CHANGED, new LoginStateChanged(newValue));
    }

    @Inject
    @Override
    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }
}
