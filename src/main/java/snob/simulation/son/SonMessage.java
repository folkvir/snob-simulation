package snob.simulation.son;

import peersim.core.Node;
import snob.simulation.rps.IMessage;

import java.util.List;

/**
 * Message containing the sample to exchange in Son
 */
public class SonMessage implements IMessage {

	private List<Node> sample;

	public SonMessage(List<Node> sample) {
		this.sample = sample;
	}

	public Object getPayload() {
		return this.sample;
	}
}
