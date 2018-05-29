package snob.simulation.son;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Node;
import snob.simulation.rps.ARandomPeerSamplingProtocol;
import snob.simulation.rps.IMessage;
import snob.simulation.rps.IRandomPeerSampling;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * The Son protocol
 */
public class Son extends ARandomPeerSamplingProtocol implements IRandomPeerSampling {
	// #A the names of the parameters in the configuration file of peersim
	private static final String PAR_C = "c"; // max partial view size
	private static final String PAR_L = "l"; // shuffle size

	// #B the values from the configuration file of peersim
	private static int c;
	private static int l;

	// #C local variables
	private SonPartialView partialView;
	private static int RND_WALK = 5;
	/**
	 * Construction of a Son instance
	 * 
	 * @param prefix
	 *            the peersim configuration
	 */
	public Son(String prefix) {
		super(prefix);
		Son.c = Configuration.getInt(prefix + "." + PAR_C);
		Son.l = Configuration.getInt(prefix + "." + PAR_L);
		this.partialView = new SonPartialView(Son.c, Son.l);
	}

	public Son() {
		super();
		this.partialView = new SonPartialView(Son.c, Son.l);
	}

	@Override
	protected boolean pFail(List<Node> path) {
		// the probability is constant since the number of hops to establish
		// a connection is constant
		double pf = 1 - Math.pow(1 - ARandomPeerSamplingProtocol.fail, 6);
		return CommonState.r.nextDouble() < pf;
	}

	public void periodicCall() {
		if (this.isUp() && this.partialView.size() > 0) {
			this.partialView.incrementAge();
			Node q = this.partialView.getOldest();
			Son qSon = (Son) q.getProtocol(ARandomPeerSamplingProtocol.pid);
			if (qSon.isUp() && !this.pFail(null)) {
				// #A if the chosen peer is alive, initiate the exchange
				List<Node> sample = this.partialView.getSample(this.node, q,
						true);
				sample.add(this.node);
				IMessage received = qSon.onPeriodicCall(this.node,
						new SonMessage(sample));
				List<Node> samplePrime = (List<Node>) received.getPayload();
				this.partialView.mergeSample(this.node, q, samplePrime, sample,
						true);
			} else {
				// #B if the chosen peer is dead, remove it from the view
				this.partialView.removeNode(q);
			}
		}
	}

	public IMessage onPeriodicCall(Node origin, IMessage message) {
		List<Node> samplePrime = this.partialView.getSample(this.node, origin,
				false);
		this.partialView.mergeSample(this.node, origin,
				(List<Node>) message.getPayload(), samplePrime, false);
		return new SonMessage(samplePrime);
	}

	public void join(Node joiner, Node contact) {
		if (this.node == null) { // lazy loading of the node identity
			this.node = joiner;
		}
		if (contact != null) { // the very first join does not have any contact
			Son contactSon = (Son) contact.getProtocol(Son.pid);
			this.partialView.clear();
			this.partialView.addNeighbor(contact);
			contactSon.onSubscription(this.node);
		}
		this.isUp = true;
	}

	public void onSubscription(Node origin) {
		List<Node> aliveNeighbors = this.getAliveNeighbors();
		Collections.shuffle(aliveNeighbors, CommonState.r);
		int nbRndWalk = Math.min(Son.c - 1, aliveNeighbors.size());

		for (int i = 0; i < nbRndWalk; ++i) {
			randomWalk(origin, aliveNeighbors.get(i), Son.RND_WALK);
		}
	}

	public void leave() {
		this.isUp = false;
		this.partialView.clear();
		// nothing else
	}

	public List<Node> getPeers(int k) {
		return this.partialView.getPeers(k);
	}

	@Override
	public IRandomPeerSampling clone() {
		try {
			Son s = new Son();
			s.partialView = (SonPartialView) this.partialView.clone();
			return s;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Perform a random walk in the network at a depth set by ttl (time-to-live)
	 * 
	 * @param origin
	 *            the subscribing peer
	 * @param current
	 *            the current peer that either accept the subcription or
	 *            forwards it
	 * @param ttl
	 *            the current time-to-live before the subscription gets accepted
	 */
	private static void randomWalk(Node origin, Node current, int ttl) {
		final Son originSon = (Son) origin.getProtocol(Son.pid);
		final Son currentSon = (Son) current.getProtocol(Son.pid);
		List<Node> aliveNeighbors = currentSon.getAliveNeighbors();
		ttl -= 1;
		// #A if the receiving peer has neighbors in its partial view
		if (aliveNeighbors.size() > 0) {
			// #A1 if the ttl is greater than 0, continue the random walk
			if (ttl > 0) {
				final Node next = aliveNeighbors.get(CommonState.r
						.nextInt(aliveNeighbors.size()));
				randomWalk(origin, next, ttl);
			} else {
				// #B if the ttl is greater than 0 or the partial view is empty,
				// then
				// accept the subscription and stop forwarding it
				if (origin.getID() != current.getID()) {
					Iterator<Node> iPeers = currentSon.getPeers(1)
							.iterator();
					if (iPeers.hasNext()) {
						Node chosen = iPeers.next();
						currentSon.partialView.removeNode(chosen);
						originSon.partialView.addNeighbor(chosen);
					}
					currentSon.addNeighbor(origin);
				}
			}
		}
	}

	@Override
	public boolean addNeighbor(Node peer) {
		return this.partialView.addNeighbor(peer);
	}

}
