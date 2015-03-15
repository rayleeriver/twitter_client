package com.swpbiz.mysimpletweets.fragments;

public class HomeTimelineFragment extends TweetsListFragment {

    public void reload() {
        populateTimeline(true);
    }

    @Override
    protected void populateTimeline(boolean reset) {
        super.populateTimeline(reset);
        getClient().getHomeTimeline(new MyJsonHttpResponseHandler());
    }

}
