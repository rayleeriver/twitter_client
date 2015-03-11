<h1>Simple Twitter Client</h1>

<p>Time taken:  10 hours</p>
<p>Licecap video in file <a href="https://github.com/rayleeriver/twitter_client/blob/master/submission_w3.gif">submission_w3.gif</a></p>

<p>Todos</p>
<ul>
<li>Remove extra space on the left Actionbar Home icon</li>
<li>Move DB operations to background task (e.g. user.save() to AsyncTask)</li>
<li>Use LRUCache for loaded images and further persiste to Localstorage</li>
<ul>
<li>Further read images from Cache if possible</li>
</ul>
</ul>

<p>The following stories have been implemented</p>

<ul>
<li>User can sign in to Twitter using OAuth login</li>
<li>User can view the tweets from their home timeline

<ul>
<li>User should be displayed the username, name, and body for each tweet</li>
<li>User should be displayed the <a href="https://gist.github.com/nesquena/f786232f5ef72f6e10a7">relative timestamp</a> for each tweet "8m", "7h"</li>
<li>User can view more tweets as they scroll with <a href="http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews">infinite pagination</a></li>
<li><strong>Optional:</strong> Links in tweets are clickable and will launch the web browser (see <a href="http://guides.codepath.com/android/Working-with-the-TextView#autolinking-urls">autolink</a>)</li>
</ul></li>
<li>User can compose a new tweet

<ul>
<li>User can click a “Compose” icon in the Action Bar on the top right</li>
<li>User can then enter a new tweet and post this to twitter</li>
<li>User is taken back to home timeline with <strong>new tweet visible</strong> in timeline</li>
<li><strong>Optional</strong>: User can see a counter with total number of characters left for tweet</li>
</ul></li>
</ul>

<p>The following advanced user stories are <strong>optional</strong>:</p>

<ul>
<li><strong>Advanced:</strong> User can refresh tweets timeline by <a href="http://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide">pulling down to refresh</a> (i.e pull-to-refresh)</li>
<li><strong>Advanced</strong>: User can open the twitter app offline and see last loaded tweets

<ul>
<li>Tweets are <a href="http://guides.codepath.com/android/ActiveAndroid-Guide">persisted into sqlite</a> and can be displayed from the local DB</li>
</ul></li>
<li><strong>Advanced:</strong> Improve the user interface and theme the app to feel "twitter branded"</li>
</ul>
