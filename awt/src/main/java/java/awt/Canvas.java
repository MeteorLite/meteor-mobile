/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * @author Dmitry A. Durnev
 */
package java.awt;

import java.awt.image.BufferStrategy;
import java.awt.image.ImageProducer;

import org.apache.harmony.luni.util.NotImplementedException;

public class Canvas extends Component {
    public Canvas() {
//        toolkit.lockAWT();
//        try {
//        } finally {
//            toolkit.unlockAWT();
//        }
    }

    public Canvas(GraphicsConfiguration a0) {
        this();
//        toolkit.lockAWT();
//        try {
//        } finally {
//            toolkit.unlockAWT();
//        }
    }

    public void update(Graphics g) {
//        toolkit.lockAWT();
//        try {
//            super.update(g);
//        } finally {
//            toolkit.unlockAWT();
//        }
    }

    public void addNotify() {
//        toolkit.lockAWT();
//        try {
//            super.addNotify();
//        } finally {
//            toolkit.unlockAWT();
//        }
    }

    public void createBufferStrategy(int a0) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void createBufferStrategy(int a0, BufferCapabilities a1) throws AWTException, NotImplementedException {
        throw new NotImplementedException();
    }

    public BufferStrategy getBufferStrategy() throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void paint(Graphics g) {
//        toolkit.lockAWT();
//        try {
//            g.clearRect(0, 0, w, h);
//        } finally {
//            toolkit.unlockAWT();
//        }
    }
    
    Dimension getDefaultMinimumSize() {
        return new Dimension(0, 0);
    }

    String autoName() {
        return "";
//        return ("canvas" + toolkit.autoNumber.nextCanvas++); //$NON-NLS-1$
    }

    public Image createImage(ImageProducer imageProducer)
    {
        return null;
    }
}

