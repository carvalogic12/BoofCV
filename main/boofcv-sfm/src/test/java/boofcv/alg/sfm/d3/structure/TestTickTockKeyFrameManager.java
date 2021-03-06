/*
 * Copyright (c) 2011-2020, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.alg.sfm.d3.structure;

import boofcv.abst.tracker.PointTrackerDefault;
import org.ddogleg.struct.GrowQueue_I32;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Peter Abeles
 */
class TestTickTockKeyFrameManager extends ChecksVisOdomKeyFrameManager {
	int maxKeyFrames = 5;

	@Test
	void alwaysAddBeforeMax() {
		var tracker = new DummyTracker();
		var visBundle = new VisOdomBundleAdjustment<>(sba, VisOdomBundleAdjustment.BTrack::new);

		var alg = new TickTockKeyFrameManager();
		alg.keyframePeriod=10000; // don't want it to add a new keyframe right afterwards

		// not used but call it just in case that changes in the future
		alg.initialize(300,200);
		// add the initial set of frames
		for (int i = 0; i < 5; i++) {
			GrowQueue_I32 discard = alg.selectFramesToDiscard(tracker,maxKeyFrames,visBundle);
			assertEquals(0,discard.size);
			visBundle.addFrame(i);
			tracker.process(null);
		}
		// add one more frame. It should now want to discard the current frame
		visBundle.addFrame(6);
		tracker.process(null);
		GrowQueue_I32 discard = alg.selectFramesToDiscard(tracker,maxKeyFrames,visBundle);
		assertEquals(1,discard.size);
		assertEquals(5,discard.get(0));
	}

	/**
	 * See if it will periodically save the current frame
	 */
	@Test
	void savePeriodically() {
		var tracker = new DummyTracker();
		var visBundle = new VisOdomBundleAdjustment<>(sba, VisOdomBundleAdjustment.BTrack::new);

		var alg = new TickTockKeyFrameManager();
		alg.keyframePeriod = 3;
		// add the initial set of frames
		for (int i = 0; i < 5; i++) {
			tracker.process(null);
			visBundle.addFrame(i);
		}

		for (int i = 0; i < 10; i++) {
			tracker.process(null);
			visBundle.addFrame(i+5);
			GrowQueue_I32 discard = alg.selectFramesToDiscard(tracker,maxKeyFrames,visBundle);
			assertEquals(1,discard.size);
			long id = tracker.getFrameID();
			if( id%3 == 0 ) {
				assertEquals(0,discard.get(0));
			} else {
				assertEquals(5,discard.get(0));
			}
			// remove the frame to make it realistic
			VisOdomBundleAdjustment.BFrame frame = visBundle.frames.get(discard.get(0));
			visBundle.removeFrame(frame,new ArrayList<>());
		}
	}

	@Override
	public VisOdomKeyFrameManager createFrameManager() {
		return new TickTockKeyFrameManager();
	}

	private static class DummyTracker extends PointTrackerDefault {}
}