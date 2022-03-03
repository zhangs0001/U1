package com.u1.gocashm.model.response;

public class GoogleGpsPhModel {


    /**
     * location : {"lat":34.0653347,"lng":-118.24389099999999}
     * accuracy : 1906
     */

    private LocationBean location;
    private double accuracy;

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public static class LocationBean {
        /**
         * lat : 34.0653347
         * lng : -118.24389099999999
         */

        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
