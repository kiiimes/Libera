/*
 *
 *  ******************************************************************************
 *   Copyright 2019 Korea University & Open Networking Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   ******************************************************************************
 *   Developed by Libera team, Operating Systems Lab of Korea University
 *   ******************************************************************************
 *
 */
package net.onrc.openvirtex.messages;

import net.onrc.openvirtex.elements.datapath.OVXSwitch;
import net.onrc.openvirtex.elements.port.OVXPort;

import org.projectfloodlight.openflow.exceptions.OFParseError;
import org.projectfloodlight.openflow.protocol.OFBadRequestCode;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFQueueGetConfigRequest;

public class OVXQueueGetConfigRequest extends OVXMessage implements
        Devirtualizable {

    public OVXQueueGetConfigRequest(OFMessage msg) {
        super(msg);
    }

    public OFQueueGetConfigRequest getQueueGetConfigRequest() {
        return (OFQueueGetConfigRequest)this.getOFMessage();
    }

    @Override
    public void devirtualize(final OVXSwitch sw) throws OFParseError {
        final OVXPort p = sw.getPort(
                this.getQueueGetConfigRequest().getPort().getShortPortNumber());
        if (p == null) {
            sw.sendMsg(
                    OVXMessageUtil.makeErrorMsg(OFBadRequestCode.EPERM, this),
                    sw
            );
            return;
        }

        OVXMessageUtil.translateXid(this, p);
    }

    @Override
    public int hashCode() {
        return this.getOFMessage().hashCode();
    }
}
