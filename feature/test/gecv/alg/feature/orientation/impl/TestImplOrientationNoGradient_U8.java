/*
 * Copyright 2011 Peter Abeles
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gecv.alg.feature.orientation.impl;

import gecv.alg.feature.orientation.GenericOrientationImageTests;
import gecv.alg.feature.orientation.OrientationNoGradient;
import gecv.struct.image.ImageUInt8;
import org.junit.Test;


/**
 * @author Peter Abeles
 */
public class TestImplOrientationNoGradient_U8 {
	double angleTol = 0.1;// had to up tolerance for limited resolution of UInt8 images
	int r = 3;

	@Test
	public void standardUnweighted() {
		GenericOrientationImageTests<ImageUInt8> tests = new GenericOrientationImageTests<ImageUInt8>();

		OrientationNoGradient<ImageUInt8> alg = new ImplOrientationNoGradient_U8(r);

		tests.setup(angleTol, r*2+1 , alg,ImageUInt8.class);
		tests.performAll();
	}
}
