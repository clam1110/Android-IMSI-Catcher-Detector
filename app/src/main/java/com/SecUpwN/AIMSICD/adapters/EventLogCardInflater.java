package com.SecUpwN.AIMSICD.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SecUpwN.AIMSICD.R;

/**
 * Description:    Class to show data of table "EventLog" in "db-View" > "EventLog"
 *
 *  Dependencies:   EventLogItemData.java
 *                  Data representation on eventlog_items.xml (from Array in DdViewerFragment)
 *                  Data query on AIMSICDDbAdapter.java
 *                  Data representation on APP see menu "db-View" > "EventLog"
 *
 *  Usage: DdViewerFragment, AIMSICDDbAdapter.java
 *
 *  Issues:
 *
 *  ChangeLog:
 *
 *
 *  Notes:
 *
 */
public class EventLogCardInflater implements IAdapterViewInflater<EventLogItemData> {

    @Override
    public View inflate(final BaseInflaterAdapter<EventLogItemData> adapter,
                        final int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.eventlog_items, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final EventLogItemData item = adapter.getTItem(pos);
        holder.updateDisplay(item);

        return convertView;
    }

    private class ViewHolder {

        private final View mRootView;
        private final TextView mTimestamp;
        private final TextView mLAC;
        private final TextView mCID;
        private final TextView mPSC;
        private final TextView mgpsd_lat;
        private final TextView mgpsd_lon;
        private final TextView mgpsd_accur;
        private final TextView mDF_id;
        private final TextView mDF_description;
        private final TextView mRecordId;

        public ViewHolder(View rootView) {
            mRootView = rootView;
            //
            mTimestamp =         (TextView) mRootView.findViewById(R.id.time);
            mLAC =          (TextView) mRootView.findViewById(R.id.LAC);
            mCID =          (TextView) mRootView.findViewById(R.id.CID);
            mPSC =          (TextView) mRootView.findViewById(R.id.PSC);
            mgpsd_lat =     (TextView) mRootView.findViewById(R.id.gpsd_lat);
            mgpsd_lon =     (TextView) mRootView.findViewById(R.id.gpsd_lon);
            mgpsd_accur =   (TextView) mRootView.findViewById(R.id.gpsd_accur);
            mDF_id =        (TextView) mRootView.findViewById(R.id.DF_id);
            mDF_description =   (TextView) mRootView.findViewById(R.id.DF_description);
            mRecordId =      (TextView) mRootView.findViewById(R.id.record_id);
            rootView.setTag(this);
        }

        public void updateDisplay(EventLogItemData item) {
            mTimestamp.setText(item.getTimestamp());
            mLAC.setText(item.getLac());
            mCID.setText(item.getCellID());
            mPSC.setText(item.getPsc());
            mgpsd_lat.setText(item.getLat());
            mgpsd_lon.setText(item.getLng());
            mgpsd_accur.setText(item.getgpsd_accur());
            mDF_id.setText(item.getDF_id());
            mDF_description.setText(item.getmDF_description());
            mRecordId.setText(item.getRecordId());
        }
    }
}
