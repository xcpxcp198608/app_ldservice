// LocationManager.aidl
package com.wiatec.ldservice;

import com.wiatec.ldservice.pojo.LocationInfo;

interface LocationManager {
    LocationInfo getLocation();
}
