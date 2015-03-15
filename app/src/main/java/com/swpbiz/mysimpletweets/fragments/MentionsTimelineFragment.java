package com.swpbiz.mysimpletweets.fragments;

public class MentionsTimelineFragment extends TweetsListFragment{

    @Override
    protected void populateTimeline(boolean reset) {
        super.populateTimeline(reset);
        getClient().getMentionsTimeline(new MyJsonHttpResponseHandler());
    }

}
