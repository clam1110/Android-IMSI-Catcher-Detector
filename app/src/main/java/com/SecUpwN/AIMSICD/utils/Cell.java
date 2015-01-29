
    /**
     * Longitude Geolocation of current Cell
     *
     * @return Longitude
     */
    public double getLon() {
        return this.lon;
    }

    /**
     * Set Longitude Geolocation of current Cell
     *
     * @param lon Longitude
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Latitude Geolocation of current Cell
     *
     * @return Latitude
     */
    public double getLat() {
        return this.lat;
    }

    /**
     * Set Latitude Geolocation of current Cell
     *
     * @param lat Latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Ground speed in metres/second
     *
     * @return Ground speed or 0.0 if unavailable
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Set current ground speed in metres/second
     *
     * @param speed Ground Speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Accuracy of location in metres
     *
     * @return Location accuracy in metres or 0.0 if unavailable
     */
    public double getAccuracy() {
        return this.accuracy;
    }

    /**
     * Set current location accuracy in metres
     *
     * @param accuracy Location accuracy
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Bearing
     *
     * @return Bearing (0.0, 360.0] or 0.0 if unavailable
     */
    public double getBearing() {
        return this.bearing;
    }

    /**
     * Set current Bearing
     *
     * @param bearing Current bearing
     */
    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    /**
     * LTE Timing Advance
     *
     * @return LTE Timing Advance or Integer.MAX_VALUE if unavailable
     */
    public int getTimingAdvance() {
        return this.timingAdvance;
    }

    /**
     * Set current LTE Timing Advance
     *
     * @param ta Current LTE Timing Advance
     */
    public void setTimingAdvance(int ta) {
        this.timingAdvance = ta;
    }

    /**
     * Network Type
     *
     * @return string representing device Network Type
     */
    public int getNetType() {
        return this.netType;
    }

    /**
     * Set current Network Type
     *
     * @param netType Current Network Type
     */
    public void setNetType(int netType) {
        this.netType = netType;
    }

    /**
     * Timestamp of current cell information
     *
     * @return Timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Set current cell information timestamp
     *
     * @param timestamp Current cell information timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Received Signal Strength
     *
     * @return Received Signal Strength Indicator (RSSI)
     */
    public int getRssi() {
        return this.rssi;
    }


    /**
     * TODO: What is this, and where is it supposed to be used ???
     *
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cid;
        result = prime * result + lac;
        result = prime * result + mcc;
        result = prime * result + mnc;
        if (psc != -1) result = prime * result + psc;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object) this).getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        if (this.psc != Integer.MAX_VALUE) {
            return this.cid == other.getCID() && this.lac == other.getLAC() && this.mcc == other
                    .getMCC() && this.mnc == other.getMNC() && this.psc == other.getPSC();
        } else {
            return this.cid == other.getCID() && this.lac == other.getLAC() && this.mcc == other
                    .getMCC() && this.mnc == other.getMNC();
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("CID - ").append(cid).append("\n");
        result.append("LAC - ").append(lac).append("\n");
        result.append("MCC - ").append(mcc).append("\n");
        result.append("MNC - ").append(mnc).append("\n");
        result.append("DBm - ").append(dbm).append("\n");
        if (psc != Integer.MAX_VALUE) {
            result.append("PSC - ").append(psc).append("\n");
        }
        result.append("Type - ").append(netType).append("\n");
        result.append("Lon - ").append(lon).append("\n");
        result.append("Lat - ").append(lat).append("\n");

        return result.toString();
    }

    public boolean isValid() {
        return this.getCID() != Integer.MAX_VALUE && this.getLAC() != Integer.MAX_VALUE;
    }


    // What is this doing ??? (And from where is it called?)
    public static class CellLookUpAsync extends AsyncTask<String, Void, List<Cell>> {
        public AsyncResponse delegate=null;

        @Override
        protected List<Cell> doInBackground(String ... urls) {
            try {
                InputStream stream;
                // Instantiate the parser
                StackOverflowXmlParser stackOverflowXmlParser = new StackOverflowXmlParser();
                List<Cell> cells;

                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                stream = conn.getInputStream();
                cells = stackOverflowXmlParser.parse(stream);

                conn.disconnect();
                stream.close();

                return cells;

            } catch (Exception e) {
                Log.i("AIMSICD", "Cell Lookup - " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Cell> cells) {
            delegate.processFinish(cells);
        }
    }

    // Parcelling
    public Cell(Parcel in){
        String[] data = new String[15];

        in.readStringArray(data);
        cid = Integer.valueOf(data[0]);
        lac = Integer.valueOf(data[1]);
        mcc = Integer.valueOf(data[2]);
        mnc = Integer.valueOf(data[3]);
        dbm = Integer.valueOf(data[4]);
        psc = Integer.valueOf(data[5]);
        rssi = Integer.valueOf(data[6]);
        timingAdvance = Integer.valueOf(data[7]);
        sid = Integer.valueOf(data[8]);
        netType = Integer.valueOf(data[9]);
        lon = Double.valueOf(data[10]);
        lat = Double.valueOf(data[11]);
        speed = Double.valueOf(data[12]);
        accuracy = Double.valueOf(data[13]);
        bearing = Double.valueOf(data[14]);
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                String.valueOf(this.cid),
                String.valueOf(this.lac),
                String.valueOf(this.mcc),
                String.valueOf(this.mnc),
                String.valueOf(this.dbm),
                String.valueOf(this.psc),
                String.valueOf(this.rssi),
                String.valueOf(this.timingAdvance),
                String.valueOf(this.sid),
                String.valueOf(this.netType),
                String.valueOf(this.lon),
                String.valueOf(this.lat),
                String.valueOf(this.speed),
                String.valueOf(this.accuracy),
                String.valueOf(this.bearing)});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };
}
