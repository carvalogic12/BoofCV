/*
 * Copyright (c) 2011-2018, Peter Abeles. All Rights Reserved.
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

package boofcv.alg.geo.pose;

import boofcv.alg.geo.h.CommonHomographyChecks;
import boofcv.struct.geo.AssociatedPair;
import georegression.struct.point.Point4D_F64;
import org.ejml.data.DMatrixRMaj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Common testing code for algorithms which estimate motion from a set of associated observations
 * and known 3D coordinates.
 *
 * @author Peter Abeles
 */
public class CommonMotionNPointHomogenous {

	protected Random rand = new Random(234);

	// the true motion
	protected DMatrixRMaj motion;
	// list of points in world reference frame
	protected List<Point4D_F64> worldPts;
	// list of points is camera reference frame
	protected List<Point4D_F64> cameraPts;
	// list of observation pairs in both reference frames
	protected List<AssociatedPair> assocPairs;

	protected void generateScene(int N, DMatrixRMaj motion, boolean planar) {
		this.motion = motion;

		// randomly generate points in space
		if( planar ) {
			worldPts = CommonHomographyChecks.createRandomPlaneH(rand, 3, N);
		} else {
//			worldPts = GeoTestingOps.randomPoints_F64(-1, 1, -1, 1, 2, 3, N, rand);
		}

		cameraPts = new ArrayList<>();

		// transform points into second camera's reference frame
		assocPairs = new ArrayList<>();
		for(Point4D_F64 X : worldPts ) {
//			Point4D_F64 p2 = PerspectiveOps.renderPixel(motion, p1, null);
//
//			AssociatedPair pair = new AssociatedPair();
//			pair.p1.set(p1.x/p1.z,p1.y/p1.z);
//			pair.p2.set(p2.x/p2.z,p2.y/p2.z);
//			assocPairs.add(pair);
//
//			cameraPts.add(p2);
		}
	}
}

