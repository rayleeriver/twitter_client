package com.swpbiz.mysimpletweets.fragments;

public class HomeTimelineFragment extends TweetsListFragment {

    @Override
    protected void populateTimeline(boolean reset) {
        super.populateTimeline(reset);
        getClient().getHomeTimeline(new MyJsonHttpResponseHandler());
    }

}
