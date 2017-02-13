/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maibo.lys.xianhuicustomer.myinterface;

import android.util.Log;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.maibo.lys.xianhuicustomer.myactivity.SplashActivity;

import java.util.List;

public class SampleMultiplePermissionListener implements MultiplePermissionsListener {

  private final SplashActivity activity;
  int number=0;


  public SampleMultiplePermissionListener(SplashActivity activity) {
    this.activity = activity;
  }

  @Override
  public void onPermissionsChecked(MultiplePermissionsReport report) {
    number=0;
    for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
      number++;
      activity.showPermissionGranted(response.getPermissionName(),number);
      Log.e("xiugai:","aaa");
    }

    for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
      activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
      Log.e("xiugai:","bbb");
    }

  }

  @Override
  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                 PermissionToken token) {
    activity.showPermissionRationale(token);
    Log.e("xiugai:","ccc");
  }
}
