<ul>
<li>GET <code>/</code> -> Renders the home page</li>
<li>User authentication routes
<ul>
<li>GET <code>/login</code> -> Renders the login view</li>
<li>GET <code>/logout</code> -> Logs the user out and redirects to <code>/</code> route</li>
<li>GET <code>/drivers</code> -> Renders the drivers page</li>
<li>GET <code>/routes</code> -> Renders the routes page</li>

<li>POST <code>/drivers/assign-route</code> -> Assign one particular route to one driver</li>

Installation</h3>

<p>(Note : These instructions are only for developers/testers for now)</p>
<ol>
<li>Open git bash or cmd</li>
<li>Clone the repo:</li>
</ol>
<pre><code>git clone https://github.com/Bucephalus-lgtm/Poriskar
</code></pre>
<ol start="3">
<li>Change to the <strong>Poriskar</strong> directory</li>
</ol>
<pre><code>cd Poriskar

</code></pre>
<ol start="4">
<li>Since the operational code is in the <code>admin-backend</code>, and the current branch is <code>master</code>, checkout a tracking branch pointing to the <code>admin-backend</code> of the remote repo (changes will get pulled automatically)</li>
</ol>

<pre><code>git checkout origin/admin-backend

</code></pre>
<ol start="5">
<li>Open your git bash or cmd again, and cd to the <strong>Poriskar</strong> directory. Then</li>

<pre><code>npm install
</code></pre>

<p>After all packages have gotten installed,</p>
<pre><code>npm run dev
</code></pre>
<p>Web app will be accessible at <code>localhost:3000</code></p>
</article>
      </div>
  </div>


  
  
</div>
