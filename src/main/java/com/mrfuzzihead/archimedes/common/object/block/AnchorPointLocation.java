package com.mrfuzzihead.archimedes.common.object.block;

import com.mrfuzzihead.movingworld.common.chunk.LocatedBlock;

/**
 * Contains two LocatedBlocks, one of the anchor in world, and one of the anchor on the ship it corresponds to.
 */

public class AnchorPointLocation {

    public LocatedBlock shipAnchor;
    public LocatedBlock worldAnchor;

    public AnchorPointLocation(LocatedBlock shipAnchor, LocatedBlock worldAnchor) {
        this.shipAnchor = shipAnchor;
        this.worldAnchor = worldAnchor;
    }

}
