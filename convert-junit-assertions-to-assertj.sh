


<!DOCTYPE html>
<html lang="en" class="">
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# object: http://ogp.me/ns/object# article: http://ogp.me/ns/article# profile: http://ogp.me/ns/profile#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Language" content="en">
    
    
    <title>assertj-core/convert-junit-assertions-to-assertj.sh at master · joel-costigliola/assertj-core</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub">
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub">
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-touch-icon-114.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-touch-icon-114.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-touch-icon-144.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-touch-icon-144.png">
    <meta property="fb:app_id" content="1401488693436528">

      <meta content="@github" name="twitter:site" /><meta content="summary" name="twitter:card" /><meta content="joel-costigliola/assertj-core" name="twitter:title" /><meta content="assertj-core - AssertJ is a library of assertions similar to fest-assert but providing a richer set of assertions." name="twitter:description" /><meta content="https://avatars1.githubusercontent.com/u/382613?v=3&amp;s=400" name="twitter:image:src" />
      <meta content="GitHub" property="og:site_name" /><meta content="object" property="og:type" /><meta content="https://avatars1.githubusercontent.com/u/382613?v=3&amp;s=400" property="og:image" /><meta content="joel-costigliola/assertj-core" property="og:title" /><meta content="https://github.com/joel-costigliola/assertj-core" property="og:url" /><meta content="assertj-core - AssertJ is a library of assertions similar to fest-assert but providing a richer set of assertions." property="og:description" />
      <meta name="browser-stats-url" content="https://api.github.com/_private/browser/stats">
    <meta name="browser-errors-url" content="https://api.github.com/_private/browser/errors">
    <link rel="assets" href="https://assets-cdn.github.com/">
    <link rel="web-socket" href="wss://live.github.com/_sockets/MTAzMTYyNTpjZDY1YThkOTFiMjAyZDQ2NTlhZjhjY2RlN2E1NWI5ZjpiMGQ2ODY3NjQ2ZWJkYjY4MGJjMGQyZGY5N2UxZDdjN2MzNjJiNWVlNDhiZDYxNGQxMzc0MDFiMDVmYTRmYTQ0--4748d914e123e801cc09ad985d51fc7b96d07365">
    <meta name="pjax-timeout" content="1000">
    <link rel="sudo-modal" href="/sessions/sudo_modal">

    <meta name="msapplication-TileImage" content="/windows-tile.png">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="selected-link" value="repo_source" data-pjax-transient>
      <meta name="google-analytics" content="UA-3769691-2">

    <meta content="collector.githubapp.com" name="octolytics-host" /><meta content="collector-cdn.github.com" name="octolytics-script-host" /><meta content="github" name="octolytics-app-id" /><meta content="4F8CDF02:1411:DAAEB8:553009C5" name="octolytics-dimension-request_id" /><meta content="1031625" name="octolytics-actor-id" /><meta content="danhaywood" name="octolytics-actor-login" /><meta content="8a7e991fcd2c6f51b4b70d4a9525ecb5e4aacfeb0a310d5a90b7f84ea31f1060" name="octolytics-actor-hash" />
    
    <meta content="Rails, view, blob#show" name="analytics-event" />
    <meta class="js-ga-set" name="dimension1" content="Logged In">
    <meta class="js-ga-set" name="dimension2" content="Header v3">
    <meta name="is-dotcom" content="true">
    <meta name="hostname" content="github.com">
    <meta name="user-login" content="danhaywood">

    
    <link rel="icon" type="image/x-icon" href="https://assets-cdn.github.com/favicon.ico">


    <meta content="authenticity_token" name="csrf-param" />
<meta content="MbGL3MdJzG7a94yjOeEWyOCb5jXHXXLKAu3TNtqaIwv5F3TzpZ7q538JUI9Ovz/jMAUcbQOtwu+YK9ZVxxioXw==" name="csrf-token" />

    <link href="https://assets-cdn.github.com/assets/github-02784141552211464e1159c492ceb9c75d7b9baba877522f68faccb088699614.css" media="all" rel="stylesheet" />
    <link href="https://assets-cdn.github.com/assets/github2-36de2bb27440106b052d6e35b088f00153f2adf18b0f151912dffc2afa52a07c.css" media="all" rel="stylesheet" />
    
    


    <meta http-equiv="x-pjax-version" content="440b7a1c2ee84193b4c55d9002d7c670">

      
  <meta name="description" content="assertj-core - AssertJ is a library of assertions similar to fest-assert but providing a richer set of assertions.">
  <meta name="go-import" content="github.com/joel-costigliola/assertj-core git https://github.com/joel-costigliola/assertj-core.git">

  <meta content="382613" name="octolytics-dimension-user_id" /><meta content="joel-costigliola" name="octolytics-dimension-user_login" /><meta content="8779606" name="octolytics-dimension-repository_id" /><meta content="joel-costigliola/assertj-core" name="octolytics-dimension-repository_nwo" /><meta content="true" name="octolytics-dimension-repository_public" /><meta content="false" name="octolytics-dimension-repository_is_fork" /><meta content="8779606" name="octolytics-dimension-repository_network_root_id" /><meta content="joel-costigliola/assertj-core" name="octolytics-dimension-repository_network_root_nwo" />
  <link href="https://github.com/joel-costigliola/assertj-core/commits/master.atom" rel="alternate" title="Recent Commits to assertj-core:master" type="application/atom+xml">

  </head>


  <body class="logged_in  env-production windows vis-public page-blob">
    <a href="#start-of-content" tabindex="1" class="accessibility-aid js-skip-to-content">Skip to content</a>
    <div class="wrapper">
      
      
      


        <div class="header header-logged-in true" role="banner">
  <div class="container clearfix">

    <a class="header-logo-invertocat" href="https://github.com/" data-hotkey="g d" aria-label="Homepage" data-ga-click="Header, go to dashboard, icon:logo">
  <span class="mega-octicon octicon-mark-github"></span>
</a>


      <div class="site-search repo-scope js-site-search" role="search">
          <form accept-charset="UTF-8" action="/joel-costigliola/assertj-core/search" class="js-site-search-form" data-global-search-url="/search" data-repo-search-url="/joel-costigliola/assertj-core/search" method="get"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /></div>
  <input type="text"
    class="js-site-search-field is-clearable"
    data-hotkey="s"
    name="q"
    placeholder="Search"
    data-global-scope-placeholder="Search GitHub"
    data-repo-scope-placeholder="Search"
    tabindex="1"
    autocapitalize="off">
  <div class="scope-badge">This repository</div>
</form>
      </div>

      <ul class="header-nav left" role="navigation">
          <li class="header-nav-item explore">
            <a class="header-nav-link" href="/explore" data-ga-click="Header, go to explore, text:explore">Explore</a>
          </li>
            <li class="header-nav-item">
              <a class="header-nav-link" href="https://gist.github.com" data-ga-click="Header, go to gist, text:gist">Gist</a>
            </li>
            <li class="header-nav-item">
              <a class="header-nav-link" href="/blog" data-ga-click="Header, go to blog, text:blog">Blog</a>
            </li>
          <li class="header-nav-item">
            <a class="header-nav-link" href="https://help.github.com" data-ga-click="Header, go to help, text:help">Help</a>
          </li>
      </ul>

      
<ul class="header-nav user-nav right" id="user-links">
  <li class="header-nav-item dropdown js-menu-container">
    <a class="header-nav-link name" href="/danhaywood" data-ga-click="Header, go to profile, text:username">
      <img alt="@danhaywood" class="avatar" data-user="1031625" height="20" src="https://avatars1.githubusercontent.com/u/1031625?v=3&amp;s=40" width="20" />
      <span class="css-truncate">
        <span class="css-truncate-target">danhaywood</span>
      </span>
    </a>
  </li>

  <li class="header-nav-item dropdown js-menu-container">
    <a class="header-nav-link js-menu-target tooltipped tooltipped-s" href="/new" aria-label="Create new..." data-ga-click="Header, create new, icon:add">
      <span class="octicon octicon-plus"></span>
      <span class="dropdown-caret"></span>
    </a>

    <div class="dropdown-menu-content js-menu-content">
      <ul class="dropdown-menu">
        
<li>
  <a href="/new" data-ga-click="Header, create new repository, icon:repo"><span class="octicon octicon-repo"></span> New repository</a>
</li>
<li>
  <a href="/organizations/new" data-ga-click="Header, create new organization, icon:organization"><span class="octicon octicon-organization"></span> New organization</a>
</li>


  <li class="dropdown-divider"></li>
  <li class="dropdown-header">
    <span title="joel-costigliola/assertj-core">This repository</span>
  </li>
    <li>
      <a href="/joel-costigliola/assertj-core/issues/new" data-ga-click="Header, create new issue, icon:issue"><span class="octicon octicon-issue-opened"></span> New issue</a>
    </li>

      </ul>
    </div>
  </li>

  <li class="header-nav-item">
        <a href="/notifications" aria-label="You have no unread notifications" class="header-nav-link notification-indicator tooltipped tooltipped-s" data-ga-click="Header, go to notifications, icon:read" data-hotkey="g n">
        <span class="mail-status all-read"></span>
        <span class="octicon octicon-inbox"></span>
</a>
  </li>

  <li class="header-nav-item">
    <a class="header-nav-link tooltipped tooltipped-s" href="/settings/profile" id="account_settings" aria-label="Settings" data-ga-click="Header, go to settings, icon:settings">
      <span class="octicon octicon-gear"></span>
    </a>
  </li>

  <li class="header-nav-item">
    <form accept-charset="UTF-8" action="/logout" class="logout-form" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="O7S60WeW74n/bLUQs+7JakMEaDpBG/TChcQcnc4IFON7tlXMWXtP7IJFNWHTMDp7xvKMajdSZLAze85UGXZLnA==" /></div>
      <button class="header-nav-link sign-out-button tooltipped tooltipped-s" aria-label="Sign out" data-ga-click="Header, sign out, icon:logout">
        <span class="octicon octicon-sign-out"></span>
      </button>
</form>  </li>

</ul>



    
  </div>
</div>

        

        


      <div id="start-of-content" class="accessibility-aid"></div>
          <div class="site" itemscope itemtype="http://schema.org/WebPage">
    <div id="js-flash-container">
      
    </div>
    <div class="pagehead repohead instapaper_ignore readability-menu">
      <div class="container">
        
<ul class="pagehead-actions">

  <li>
      <form accept-charset="UTF-8" action="/notifications/subscribe" class="js-social-container" data-autosubmit="true" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="bzD4RXpJwSpwXPaRSZjL6PbVceIDblQO96s27piPUpmAX3mU65Fdz+Jpav7Mr42caMPSkLf7nO8yEpJcQziCcg==" /></div>    <input id="repository_id" name="repository_id" type="hidden" value="8779606" />

      <div class="select-menu js-menu-container js-select-menu">
        <a href="/joel-costigliola/assertj-core/subscription"
          class="btn btn-sm btn-with-count select-menu-button js-menu-target" role="button" tabindex="0" aria-haspopup="true"
          data-ga-click="Repository, click Watch settings, action:blob#show">
          <span class="js-select-button">
            <span class="octicon octicon-eye"></span>
            Watch
          </span>
        </a>
        <a class="social-count js-social-count" href="/joel-costigliola/assertj-core/watchers">
          34
        </a>

        <div class="select-menu-modal-holder">
          <div class="select-menu-modal subscription-menu-modal js-menu-content" aria-hidden="true">
            <div class="select-menu-header">
              <span class="select-menu-title">Notifications</span>
              <span class="octicon octicon-x js-menu-close" role="button" aria-label="Close"></span>
            </div>

            <div class="select-menu-list js-navigation-container" role="menu">

              <div class="select-menu-item js-navigation-item selected" role="menuitem" tabindex="0">
                <span class="select-menu-item-icon octicon octicon-check"></span>
                <div class="select-menu-item-text">
                  <input checked="checked" id="do_included" name="do" type="radio" value="included" />
                  <span class="select-menu-item-heading">Not watching</span>
                  <span class="description">Be notified when participating or @mentioned.</span>
                  <span class="js-select-button-text hidden-select-button-text">
                    <span class="octicon octicon-eye"></span>
                    Watch
                  </span>
                </div>
              </div>

              <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
                <span class="select-menu-item-icon octicon octicon octicon-check"></span>
                <div class="select-menu-item-text">
                  <input id="do_subscribed" name="do" type="radio" value="subscribed" />
                  <span class="select-menu-item-heading">Watching</span>
                  <span class="description">Be notified of all conversations.</span>
                  <span class="js-select-button-text hidden-select-button-text">
                    <span class="octicon octicon-eye"></span>
                    Unwatch
                  </span>
                </div>
              </div>

              <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
                <span class="select-menu-item-icon octicon octicon-check"></span>
                <div class="select-menu-item-text">
                  <input id="do_ignore" name="do" type="radio" value="ignore" />
                  <span class="select-menu-item-heading">Ignoring</span>
                  <span class="description">Never be notified.</span>
                  <span class="js-select-button-text hidden-select-button-text">
                    <span class="octicon octicon-mute"></span>
                    Stop ignoring
                  </span>
                </div>
              </div>

            </div>

          </div>
        </div>
      </div>
</form>
  </li>

  <li>
    
  <div class="js-toggler-container js-social-container starring-container ">

    <form accept-charset="UTF-8" action="/joel-costigliola/assertj-core/unstar" class="js-toggler-form starred js-unstar-button" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="uqswIN3OOasPvC729lzeka1t+erzNkOqUoolvlI+O7QDkWoPvzX2EKzPBpY93cV8mc76ppdDSBhzPolFI5XccQ==" /></div>
      <button
        class="btn btn-sm btn-with-count js-toggler-target"
        aria-label="Unstar this repository" title="Unstar joel-costigliola/assertj-core"
        data-ga-click="Repository, click unstar button, action:blob#show; text:Unstar">
        <span class="octicon octicon-star"></span>
        Unstar
      </button>
        <a class="social-count js-social-count" href="/joel-costigliola/assertj-core/stargazers">
          385
        </a>
</form>
    <form accept-charset="UTF-8" action="/joel-costigliola/assertj-core/star" class="js-toggler-form unstarred js-star-button" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="3jAYtyuB8sGmTk4UY20YrXM3bxna4FXEALVWd0dzYtx6wyYRLepWX2CYLRfYTnwIGqteE4zl8h5b2NPPRlZU7Q==" /></div>
      <button
        class="btn btn-sm btn-with-count js-toggler-target"
        aria-label="Star this repository" title="Star joel-costigliola/assertj-core"
        data-ga-click="Repository, click star button, action:blob#show; text:Star">
        <span class="octicon octicon-star"></span>
        Star
      </button>
        <a class="social-count js-social-count" href="/joel-costigliola/assertj-core/stargazers">
          385
        </a>
</form>  </div>

  </li>

        <li>
          <a href="#fork-destination-box" class="btn btn-sm btn-with-count"
              title="Fork your own copy of joel-costigliola/assertj-core to your account"
              aria-label="Fork your own copy of joel-costigliola/assertj-core to your account"
              rel="facebox"
              data-ga-click="Repository, show fork modal, action:blob#show; text:Fork">
            <span class="octicon octicon-repo-forked"></span>
            Fork
          </a>
          <a href="/joel-costigliola/assertj-core/network" class="social-count">95</a>

          <div id="fork-destination-box" style="display: none;">
            <h2 class="facebox-header">Where should we fork this repository?</h2>
            <include-fragment src=""
                class="js-fork-select-fragment fork-select-fragment"
                data-url="/joel-costigliola/assertj-core/fork?fragment=1">
              <img alt="Loading" height="64" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-128-338974454bb5c32803e82f601beb051d373744b024fe8742a76009700fd7e033.gif" width="64" />
            </include-fragment>
          </div>
        </li>

</ul>

        <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
          <span class="mega-octicon octicon-repo"></span>
          <span class="author"><a href="/joel-costigliola" class="url fn" itemprop="url" rel="author"><span itemprop="title">joel-costigliola</span></a></span><!--
       --><span class="path-divider">/</span><!--
       --><strong><a href="/joel-costigliola/assertj-core" class="js-current-repository" data-pjax="#js-repo-pjax-container">assertj-core</a></strong>

          <span class="page-context-loader">
            <img alt="" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
          </span>

        </h1>
      </div><!-- /.container -->
    </div><!-- /.repohead -->

    <div class="container">
      <div class="repository-with-sidebar repo-container new-discussion-timeline  ">
        <div class="repository-sidebar clearfix">
            
<nav class="sunken-menu repo-nav js-repo-nav js-sidenav-container-pjax js-octicon-loaders"
     role="navigation"
     data-pjax="#js-repo-pjax-container"
     data-issue-count-url="/joel-costigliola/assertj-core/issues/counts">
  <ul class="sunken-menu-group">
    <li class="tooltipped tooltipped-w" aria-label="Code">
      <a href="/joel-costigliola/assertj-core" aria-label="Code" class="selected js-selected-navigation-item sunken-menu-item" data-hotkey="g c" data-selected-links="repo_source repo_downloads repo_commits repo_releases repo_tags repo_branches /joel-costigliola/assertj-core">
        <span class="octicon octicon-code"></span> <span class="full-word">Code</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>

      <li class="tooltipped tooltipped-w" aria-label="Issues">
        <a href="/joel-costigliola/assertj-core/issues" aria-label="Issues" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g i" data-selected-links="repo_issues repo_labels repo_milestones /joel-costigliola/assertj-core/issues">
          <span class="octicon octicon-issue-opened"></span> <span class="full-word">Issues</span>
          <span class="js-issue-replace-counter"></span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>      </li>

    <li class="tooltipped tooltipped-w" aria-label="Pull requests">
      <a href="/joel-costigliola/assertj-core/pulls" aria-label="Pull requests" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g p" data-selected-links="repo_pulls /joel-costigliola/assertj-core/pulls">
          <span class="octicon octicon-git-pull-request"></span> <span class="full-word">Pull requests</span>
          <span class="js-pull-replace-counter"></span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>

      <li class="tooltipped tooltipped-w" aria-label="Wiki">
        <a href="/joel-costigliola/assertj-core/wiki" aria-label="Wiki" class="js-selected-navigation-item sunken-menu-item" data-hotkey="g w" data-selected-links="repo_wiki /joel-costigliola/assertj-core/wiki">
          <span class="octicon octicon-book"></span> <span class="full-word">Wiki</span>
          <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>      </li>
  </ul>
  <div class="sunken-menu-separator"></div>
  <ul class="sunken-menu-group">

    <li class="tooltipped tooltipped-w" aria-label="Pulse">
      <a href="/joel-costigliola/assertj-core/pulse" aria-label="Pulse" class="js-selected-navigation-item sunken-menu-item" data-selected-links="pulse /joel-costigliola/assertj-core/pulse">
        <span class="octicon octicon-pulse"></span> <span class="full-word">Pulse</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>

    <li class="tooltipped tooltipped-w" aria-label="Graphs">
      <a href="/joel-costigliola/assertj-core/graphs" aria-label="Graphs" class="js-selected-navigation-item sunken-menu-item" data-selected-links="repo_graphs repo_contributors /joel-costigliola/assertj-core/graphs">
        <span class="octicon octicon-graph"></span> <span class="full-word">Graphs</span>
        <img alt="" class="mini-loader" height="16" src="https://assets-cdn.github.com/assets/spinners/octocat-spinner-32-e513294efa576953719e4e2de888dd9cf929b7d62ed8d05f25e731d02452ab6c.gif" width="16" />
</a>    </li>
  </ul>


</nav>

              <div class="only-with-full-nav">
                  
<div class="clone-url open"
  data-protocol-type="http"
  data-url="/users/set_protocol?protocol_selector=http&amp;protocol_type=clone">
  <h3><span class="text-emphasized">HTTPS</span> clone URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="https://github.com/joel-costigliola/assertj-core.git" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" data-copy-hint="Copy to clipboard" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  
<div class="clone-url "
  data-protocol-type="ssh"
  data-url="/users/set_protocol?protocol_selector=ssh&amp;protocol_type=clone">
  <h3><span class="text-emphasized">SSH</span> clone URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="git@github.com:joel-costigliola/assertj-core.git" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" data-copy-hint="Copy to clipboard" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>

  
<div class="clone-url "
  data-protocol-type="subversion"
  data-url="/users/set_protocol?protocol_selector=subversion&amp;protocol_type=clone">
  <h3><span class="text-emphasized">Subversion</span> checkout URL</h3>
  <div class="input-group js-zeroclipboard-container">
    <input type="text" class="input-mini input-monospace js-url-field js-zeroclipboard-target"
           value="https://github.com/joel-costigliola/assertj-core" readonly="readonly">
    <span class="input-group-button">
      <button aria-label="Copy to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" data-copy-hint="Copy to clipboard" type="button"><span class="octicon octicon-clippy"></span></button>
    </span>
  </div>
</div>



<p class="clone-options">You can clone with
  <a href="#" class="js-clone-selector" data-protocol="http">HTTPS</a>, <a href="#" class="js-clone-selector" data-protocol="ssh">SSH</a>, or <a href="#" class="js-clone-selector" data-protocol="subversion">Subversion</a>.
  <a href="https://help.github.com/articles/which-remote-url-should-i-use" class="help tooltipped tooltipped-n" aria-label="Get help on which URL is right for you.">
    <span class="octicon octicon-question"></span>
  </a>
</p>


  <a href="github-windows://openRepo/https://github.com/joel-costigliola/assertj-core" class="btn btn-sm sidebar-button" title="Save joel-costigliola/assertj-core to your computer and use it in GitHub Desktop." aria-label="Save joel-costigliola/assertj-core to your computer and use it in GitHub Desktop.">
    <span class="octicon octicon-device-desktop"></span>
    Clone in Desktop
  </a>


                <a href="/joel-costigliola/assertj-core/archive/master.zip"
                   class="btn btn-sm sidebar-button"
                   aria-label="Download the contents of joel-costigliola/assertj-core as a zip file"
                   title="Download the contents of joel-costigliola/assertj-core as a zip file"
                   rel="nofollow">
                  <span class="octicon octicon-cloud-download"></span>
                  Download ZIP
                </a>
              </div>
        </div><!-- /.repository-sidebar -->

        <div id="js-repo-pjax-container" class="repository-content context-loader-container" data-pjax-container>
          

<a href="/joel-costigliola/assertj-core/blob/e46b6b5eb0529916b7b6c9dd27d564ffb57418ef/src/main/scripts/convert-junit-assertions-to-assertj.sh" class="hidden js-permalink-shortcut" data-hotkey="y">Permalink</a>

<!-- blob contrib key: blob_contributors:v21:9852a8bfcbf8d431f8725a767667cc54 -->

<div class="file-navigation js-zeroclipboard-container">
  
<div class="select-menu js-menu-container js-select-menu left">
  <span class="btn btn-sm select-menu-button js-menu-target css-truncate" data-hotkey="w"
    data-master-branch="master"
    data-ref="master"
    title="master"
    role="button" aria-label="Switch branches or tags" tabindex="0" aria-haspopup="true">
    <span class="octicon octicon-git-branch"></span>
    <i>branch:</i>
    <span class="js-select-button css-truncate-target">master</span>
  </span>

  <div class="select-menu-modal-holder js-menu-content js-navigation-container" data-pjax aria-hidden="true">

    <div class="select-menu-modal">
      <div class="select-menu-header">
        <span class="select-menu-title">Switch branches/tags</span>
        <span class="octicon octicon-x js-menu-close" role="button" aria-label="Close"></span>
      </div>

      <div class="select-menu-filters">
        <div class="select-menu-text-filter">
          <input type="text" aria-label="Filter branches/tags" id="context-commitish-filter-field" class="js-filterable-field js-navigation-enable" placeholder="Filter branches/tags">
        </div>
        <div class="select-menu-tabs">
          <ul>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="branches" data-filter-placeholder="Filter branches/tags" class="js-select-menu-tab">Branches</a>
            </li>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="tags" data-filter-placeholder="Find a tag…" class="js-select-menu-tab">Tags</a>
            </li>
          </ul>
        </div>
      </div>

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="branches">

        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/joel-costigliola/assertj-core/blob/1.7.1/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="1.7.1"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="1.7.1">
                1.7.1
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/joel-costigliola/assertj-core/blob/gh-pages/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="gh-pages"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="gh-pages">
                gh-pages
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/joel-costigliola/assertj-core/blob/java-8/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="java-8"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="java-8">
                java-8
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open selected"
               href="/joel-costigliola/assertj-core/blob/master/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="master"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="master">
                master
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/joel-costigliola/assertj-core/blob/path-assert/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="path-assert"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="path-assert">
                path-assert
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/joel-costigliola/assertj-core/blob/remove-cglib/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="remove-cglib"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="remove-cglib">
                remove-cglib
              </span>
            </a>
            <a class="select-menu-item js-navigation-item js-navigation-open "
               href="/joel-costigliola/assertj-core/blob/xml/src/main/scripts/convert-junit-assertions-to-assertj.sh"
               data-name="xml"
               data-skip-pjax="true"
               rel="nofollow">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <span class="select-menu-item-text css-truncate-target" title="xml">
                xml
              </span>
            </a>
        </div>

          <div class="select-menu-no-results">Nothing to show</div>
      </div>

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="tags">
        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-java8-1.0.0m1/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-java8-1.0.0m1"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-java8-1.0.0m1">assertj-core-java8-1.0.0m1</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-3.0.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-3.0.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-3.0.0">assertj-core-3.0.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-2.0.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-2.0.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-2.0.0">assertj-core-2.0.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.7.1/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.7.1"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.7.1">assertj-core-1.7.1</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.7.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.7.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.7.0">assertj-core-1.7.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.6.1/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.6.1"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.6.1">assertj-core-1.6.1</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.6.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.6.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.6.0">assertj-core-1.6.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.5.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.5.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.5.0">assertj-core-1.5.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.4.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.4.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.4.0">assertj-core-1.4.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.3.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.3.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.3.0">assertj-core-1.3.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.2.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.2.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.2.0">assertj-core-1.2.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.1.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.1.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.1.0">assertj-core-1.1.0</a>
            </div>
            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/joel-costigliola/assertj-core/tree/assertj-core-1.0.0/src/main/scripts/convert-junit-assertions-to-assertj.sh"
                 data-name="assertj-core-1.0.0"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text css-truncate-target"
                 title="assertj-core-1.0.0">assertj-core-1.0.0</a>
            </div>
        </div>

        <div class="select-menu-no-results">Nothing to show</div>
      </div>

    </div>
  </div>
</div>

  <div class="btn-group right">
    <a href="/joel-costigliola/assertj-core/find/master"
          class="js-show-file-finder btn btn-sm empty-icon tooltipped tooltipped-s"
          data-pjax
          data-hotkey="t"
          aria-label="Quickly jump between files">
      <span class="octicon octicon-list-unordered"></span>
    </a>
    <button aria-label="Copy file path to clipboard" class="js-zeroclipboard btn btn-sm zeroclipboard-button tooltipped tooltipped-s" data-copied-hint="Copied!" data-copy-hint="Copy file path to clipboard" type="button"><span class="octicon octicon-clippy"></span></button>
  </div>

  <div class="breadcrumb js-zeroclipboard-target">
    <span class='repo-root js-repo-root'><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/joel-costigliola/assertj-core" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">assertj-core</span></a></span></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/joel-costigliola/assertj-core/tree/master/src" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">src</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/joel-costigliola/assertj-core/tree/master/src/main" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">main</span></a></span><span class="separator">/</span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/joel-costigliola/assertj-core/tree/master/src/main/scripts" class="" data-branch="master" data-direction="back" data-pjax="true" itemscope="url"><span itemprop="title">scripts</span></a></span><span class="separator">/</span><strong class="final-path">convert-junit-assertions-to-assertj.sh</strong>
  </div>
</div>


  <div class="commit file-history-tease">
    <div class="file-history-tease-header">
        <img alt="@abalhier" class="avatar" data-user="4090574" height="24" src="https://avatars3.githubusercontent.com/u/4090574?v=3&amp;s=48" width="24" />
        <span class="author"><a href="/abalhier" rel="contributor">abalhier</a></span>
        <time datetime="2014-10-18T13:47:20Z" is="relative-time">Oct 18, 2014</time>
        <div class="commit-title">
            <a href="/joel-costigliola/assertj-core/commit/9a9294d19c9205ecf232110fe39f942e371c8a9d" class="message" data-pjax="true" title="Fixes #252 : Using within instead of offset in conversion scripts">Fixes</a> <a href="https://github.com/joel-costigliola/assertj-core/issues/252" class="issue-link" title="conversion scripts should use within instead of offset for real numbers assertions">#252</a> <a href="/joel-costigliola/assertj-core/commit/9a9294d19c9205ecf232110fe39f942e371c8a9d" class="message" data-pjax="true" title="Fixes #252 : Using within instead of offset in conversion scripts">: Using within instead of offset in conversion scripts</a>
        </div>
    </div>

    <div class="participation">
      <p class="quickstat">
        <a href="#blob_contributors_box" rel="facebox">
          <strong>3</strong>
           contributors
        </a>
      </p>
          <a class="avatar-link tooltipped tooltipped-s" aria-label="joel-costigliola" href="/joel-costigliola/assertj-core/commits/master/src/main/scripts/convert-junit-assertions-to-assertj.sh?author=joel-costigliola"><img alt="@joel-costigliola" class="avatar" data-user="382613" height="20" src="https://avatars3.githubusercontent.com/u/382613?v=3&amp;s=40" width="20" /> </a>
    <a class="avatar-link tooltipped tooltipped-s" aria-label="twillouer" href="/joel-costigliola/assertj-core/commits/master/src/main/scripts/convert-junit-assertions-to-assertj.sh?author=twillouer"><img alt="@twillouer" class="avatar" data-user="2970220" height="20" src="https://avatars1.githubusercontent.com/u/2970220?v=3&amp;s=40" width="20" /> </a>
    <a class="avatar-link tooltipped tooltipped-s" aria-label="abalhier" href="/joel-costigliola/assertj-core/commits/master/src/main/scripts/convert-junit-assertions-to-assertj.sh?author=abalhier"><img alt="@abalhier" class="avatar" data-user="4090574" height="20" src="https://avatars1.githubusercontent.com/u/4090574?v=3&amp;s=40" width="20" /> </a>


    </div>
    <div id="blob_contributors_box" style="display:none">
      <h2 class="facebox-header">Users who have contributed to this file</h2>
      <ul class="facebox-user-list">
          <li class="facebox-user-list-item">
            <img alt="@joel-costigliola" data-user="382613" height="24" src="https://avatars1.githubusercontent.com/u/382613?v=3&amp;s=48" width="24" />
            <a href="/joel-costigliola">joel-costigliola</a>
          </li>
          <li class="facebox-user-list-item">
            <img alt="@twillouer" data-user="2970220" height="24" src="https://avatars3.githubusercontent.com/u/2970220?v=3&amp;s=48" width="24" />
            <a href="/twillouer">twillouer</a>
          </li>
          <li class="facebox-user-list-item">
            <img alt="@abalhier" data-user="4090574" height="24" src="https://avatars3.githubusercontent.com/u/4090574?v=3&amp;s=48" width="24" />
            <a href="/abalhier">abalhier</a>
          </li>
      </ul>
    </div>
  </div>

<div class="file">
  <div class="file-header">
    <div class="file-actions">

      <div class="btn-group">
        <a href="/joel-costigliola/assertj-core/raw/master/src/main/scripts/convert-junit-assertions-to-assertj.sh" class="btn btn-sm " id="raw-url">Raw</a>
          <a href="/joel-costigliola/assertj-core/blame/master/src/main/scripts/convert-junit-assertions-to-assertj.sh" class="btn btn-sm js-update-url-with-hash">Blame</a>
        <a href="/joel-costigliola/assertj-core/commits/master/src/main/scripts/convert-junit-assertions-to-assertj.sh" class="btn btn-sm " rel="nofollow">History</a>
      </div>

        <a class="octicon-btn tooltipped tooltipped-nw"
           href="github-windows://openRepo/https://github.com/joel-costigliola/assertj-core?branch=master&amp;filepath=src%2Fmain%2Fscripts%2Fconvert-junit-assertions-to-assertj.sh"
           aria-label="Open this file in GitHub for Windows">
            <span class="octicon octicon-device-desktop"></span>
        </a>

            <form accept-charset="UTF-8" action="/joel-costigliola/assertj-core/edit/master/src/main/scripts/convert-junit-assertions-to-assertj.sh" class="inline-form" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="GBDVZuuvLi8uuPpWxxZKTMvXgJZ1i3UnNAz/GP3eaSAmeyUrcZd6WKiBZzxpEO6slai6M6kIcW1Me5yLghtOJw==" /></div>
              <button class="octicon-btn tooltipped tooltipped-n" type="submit" aria-label="Fork this project and edit the file" data-hotkey="e" data-disable-with>
                <span class="octicon octicon-pencil"></span>
              </button>
</form>
          <form accept-charset="UTF-8" action="/joel-costigliola/assertj-core/delete/master/src/main/scripts/convert-junit-assertions-to-assertj.sh" class="inline-form" method="post"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="rpgSc5xwwUiolVaRdbruBkSulT6M4DMg3M4x/JN5wgOzRtkKZGSX8wb6ak6phrCYhT03gGducFI4ZM2vViCw5A==" /></div>
            <button class="octicon-btn octicon-btn-danger tooltipped tooltipped-n" type="submit" aria-label="Fork this project and delete this file" data-disable-with>
              <span class="octicon octicon-trashcan"></span>
            </button>
</form>    </div>

    <div class="file-info">
        <span class="file-mode" title="File mode">executable file</span>
        <span class="file-info-divider"></span>
        89 lines (73 sloc)
        <span class="file-info-divider"></span>
      6.779 kb
    </div>
  </div>
  
  <div class="blob-wrapper data type-shell">
      <table class="highlight tab-size-8 js-file-line-container">
      <tr>
        <td id="L1" class="blob-num js-line-number" data-line-number="1"></td>
        <td id="LC1" class="blob-code js-file-line"><span class="pl-c">#!/bin/bash</span></td>
      </tr>
      <tr>
        <td id="L2" class="blob-num js-line-number" data-line-number="2"></td>
        <td id="LC2" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L3" class="blob-num js-line-number" data-line-number="3"></td>
        <td id="LC3" class="blob-code js-file-line"><span class="pl-k">function</span> <span class="pl-en">usage()</span> {</td>
      </tr>
      <tr>
        <td id="L4" class="blob-num js-line-number" data-line-number="4"></td>
        <td id="LC4" class="blob-code js-file-line">  <span class="pl-c1">echo</span></td>
      </tr>
      <tr>
        <td id="L5" class="blob-num js-line-number" data-line-number="5"></td>
        <td id="LC5" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>NAME<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L6" class="blob-num js-line-number" data-line-number="6"></td>
        <td id="LC6" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>convert-junit-assertions-to-assertj.sh - Convert most of JUnit assertions to AssertJ assertions<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L7" class="blob-num js-line-number" data-line-number="7"></td>
        <td id="LC7" class="blob-code js-file-line">  <span class="pl-c1">echo</span></td>
      </tr>
      <tr>
        <td id="L8" class="blob-num js-line-number" data-line-number="8"></td>
        <td id="LC8" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>It is difficult to convert ALL JUnit assertions (e.g. the ones that are multiline) but it should be good for most of them.<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L9" class="blob-num js-line-number" data-line-number="9"></td>
        <td id="LC9" class="blob-code js-file-line">  <span class="pl-c1">echo</span></td>
      </tr>
      <tr>
        <td id="L10" class="blob-num js-line-number" data-line-number="10"></td>
        <td id="LC10" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>SYNOPSIS<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L11" class="blob-num js-line-number" data-line-number="11"></td>
        <td id="LC11" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>convert-junit-assertions-to-assertj.sh [Pattern]<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L12" class="blob-num js-line-number" data-line-number="12"></td>
        <td id="LC12" class="blob-code js-file-line">  <span class="pl-c1">echo</span></td>
      </tr>
      <tr>
        <td id="L13" class="blob-num js-line-number" data-line-number="13"></td>
        <td id="LC13" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>OPTIONS<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L14" class="blob-num js-line-number" data-line-number="14"></td>
        <td id="LC14" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span> -h --help    this help<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L15" class="blob-num js-line-number" data-line-number="15"></td>
        <td id="LC15" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span> [Pattern]    a find pattern, default to *Test.java if you don&#39;t provide a pattern<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L16" class="blob-num js-line-number" data-line-number="16"></td>
        <td id="LC16" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>              don&#39;t forget to enclose your pattern with double quotes <span class="pl-cce">\&quot;\&quot;</span> to avoid pattern to be expanded by your shell prematurely<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L17" class="blob-num js-line-number" data-line-number="17"></td>
        <td id="LC17" class="blob-code js-file-line">  <span class="pl-c1">echo</span></td>
      </tr>
      <tr>
        <td id="L18" class="blob-num js-line-number" data-line-number="18"></td>
        <td id="LC18" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>EXAMPLE<span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L19" class="blob-num js-line-number" data-line-number="19"></td>
        <td id="LC19" class="blob-code js-file-line">  <span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span> convert-junit-assertions-to-assertj.sh <span class="pl-cce">\&quot;</span>*IT.java<span class="pl-cce">\&quot;</span><span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L20" class="blob-num js-line-number" data-line-number="20"></td>
        <td id="LC20" class="blob-code js-file-line">  <span class="pl-c1">exit</span> 0</td>
      </tr>
      <tr>
        <td id="L21" class="blob-num js-line-number" data-line-number="21"></td>
        <td id="LC21" class="blob-code js-file-line">}</td>
      </tr>
      <tr>
        <td id="L22" class="blob-num js-line-number" data-line-number="22"></td>
        <td id="LC22" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L23" class="blob-num js-line-number" data-line-number="23"></td>
        <td id="LC23" class="blob-code js-file-line"><span class="pl-k">if</span> [ <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$1</span><span class="pl-pds">&quot;</span></span> == <span class="pl-s"><span class="pl-pds">&quot;</span>-h<span class="pl-pds">&quot;</span></span> -o <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$1</span><span class="pl-pds">&quot;</span></span> == <span class="pl-s"><span class="pl-pds">&quot;</span>--help<span class="pl-pds">&quot;</span></span> ] <span class="pl-k">;</span></td>
      </tr>
      <tr>
        <td id="L24" class="blob-num js-line-number" data-line-number="24"></td>
        <td id="LC24" class="blob-code js-file-line"><span class="pl-k">then</span></td>
      </tr>
      <tr>
        <td id="L25" class="blob-num js-line-number" data-line-number="25"></td>
        <td id="LC25" class="blob-code js-file-line"> usage</td>
      </tr>
      <tr>
        <td id="L26" class="blob-num js-line-number" data-line-number="26"></td>
        <td id="LC26" class="blob-code js-file-line"><span class="pl-k">fi</span></td>
      </tr>
      <tr>
        <td id="L27" class="blob-num js-line-number" data-line-number="27"></td>
        <td id="LC27" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L28" class="blob-num js-line-number" data-line-number="28"></td>
        <td id="LC28" class="blob-code js-file-line">SED_OPTIONS=-i</td>
      </tr>
      <tr>
        <td id="L29" class="blob-num js-line-number" data-line-number="29"></td>
        <td id="LC29" class="blob-code js-file-line">FILES_PATTERN=<span class="pl-smi">${1<span class="pl-k">:-*</span>Test.java}</span></td>
      </tr>
      <tr>
        <td id="L30" class="blob-num js-line-number" data-line-number="30"></td>
        <td id="LC30" class="blob-code js-file-line"><span class="pl-c"># echo &quot;SED_OPTIONS = ${SED_OPTIONS}&quot;</span></td>
      </tr>
      <tr>
        <td id="L31" class="blob-num js-line-number" data-line-number="31"></td>
        <td id="LC31" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L32" class="blob-num js-line-number" data-line-number="32"></td>
        <td id="LC32" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span><span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L33" class="blob-num js-line-number" data-line-number="33"></td>
        <td id="LC33" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&quot;</span>Converting JUnit assertions to AssertJ assertions on files matching pattern : <span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span></td>
      </tr>
      <tr>
        <td id="L34" class="blob-num js-line-number" data-line-number="34"></td>
        <td id="LC34" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span><span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L35" class="blob-num js-line-number" data-line-number="35"></td>
        <td id="LC35" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 1 - Replacing : assertEquals(0, myList.size()) ................. by : assertThat(myList).isEmpty()<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L36" class="blob-num js-line-number" data-line-number="36"></td>
        <td id="LC36" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals(\(\&quot;.*\&quot;\),[[:blank:]]*0,[[:blank:]]*\(.*\).size())/assertThat(\2).as(\1).isEmpty()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L37" class="blob-num js-line-number" data-line-number="37"></td>
        <td id="LC37" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals([[:blank:]]*0,[[:blank:]]*\(.*\).size())/assertThat(\1).isEmpty()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L38" class="blob-num js-line-number" data-line-number="38"></td>
        <td id="LC38" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L39" class="blob-num js-line-number" data-line-number="39"></td>
        <td id="LC39" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 2 - Replacing : assertEquals(expectedSize, myList.size()) ...... by : assertThat(myList).hasSize(expectedSize)<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L40" class="blob-num js-line-number" data-line-number="40"></td>
        <td id="LC40" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals(\(\&quot;.*\&quot;\),[[:blank:]]*\([[:digit:]]*\),[[:blank:]]*\(.*\).size())/assertThat(\3).as(\1).hasSize(\2)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L41" class="blob-num js-line-number" data-line-number="41"></td>
        <td id="LC41" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals([[:blank:]]*\([[:digit:]]*\),[[:blank:]]*\(.*\).size())/assertThat(\2).hasSize(\1)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L42" class="blob-num js-line-number" data-line-number="42"></td>
        <td id="LC42" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L43" class="blob-num js-line-number" data-line-number="43"></td>
        <td id="LC43" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 3 - Replacing : assertEquals(expectedDouble, actual, delta) .... by : assertThat(actual).isCloseTo(expectedDouble, within(delta))<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L44" class="blob-num js-line-number" data-line-number="44"></td>
        <td id="LC44" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isCloseTo(\2, within(\4))/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L45" class="blob-num js-line-number" data-line-number="45"></td>
        <td id="LC45" class="blob-code js-file-line"><span class="pl-c"># must be done before assertEquals(&quot;description&quot;, expected, actual) -&gt; assertThat(actual).as(&quot;description&quot;).isEqualTo(expected) </span></td>
      </tr>
      <tr>
        <td id="L46" class="blob-num js-line-number" data-line-number="46"></td>
        <td id="LC46" class="blob-code js-file-line"><span class="pl-c"># will only replace triplet without double quote to avoid matching : assertEquals(&quot;description&quot;, expected, actual)</span></td>
      </tr>
      <tr>
        <td id="L47" class="blob-num js-line-number" data-line-number="47"></td>
        <td id="LC47" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals([[:blank:]]*\([^&quot;]*\),[[:blank:]]*\([^&quot;]*\),[[:blank:]]*\([^&quot;]*\))/assertThat(\2).isCloseTo(\1, within(\3))/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L48" class="blob-num js-line-number" data-line-number="48"></td>
        <td id="LC48" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L49" class="blob-num js-line-number" data-line-number="49"></td>
        <td id="LC49" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 4 - Replacing : assertEquals(expected, actual) ................. by : assertThat(actual).isEqualTo(expected)<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L50" class="blob-num js-line-number" data-line-number="50"></td>
        <td id="LC50" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isEqualTo(\2)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L51" class="blob-num js-line-number" data-line-number="51"></td>
        <td id="LC51" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertEquals([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isEqualTo(\1)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L52" class="blob-num js-line-number" data-line-number="52"></td>
        <td id="LC52" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L53" class="blob-num js-line-number" data-line-number="53"></td>
        <td id="LC53" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 5 - Replacing : assertArrayEquals(expectedArray, actual) ....... by : assertThat(actual).isEqualTo(expectedArray)<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L54" class="blob-num js-line-number" data-line-number="54"></td>
        <td id="LC54" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertArrayEquals(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isEqualTo(\2)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L55" class="blob-num js-line-number" data-line-number="55"></td>
        <td id="LC55" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertArrayEquals([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isEqualTo(\1)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L56" class="blob-num js-line-number" data-line-number="56"></td>
        <td id="LC56" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L57" class="blob-num js-line-number" data-line-number="57"></td>
        <td id="LC57" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 6 - Replacing : assertNull(actual) ............................. by : assertThat(actual).isNull()<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L58" class="blob-num js-line-number" data-line-number="58"></td>
        <td id="LC58" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertNull(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isNull()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L59" class="blob-num js-line-number" data-line-number="59"></td>
        <td id="LC59" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertNull([[:blank:]]*\(.*\))/assertThat(\1).isNull()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L60" class="blob-num js-line-number" data-line-number="60"></td>
        <td id="LC60" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L61" class="blob-num js-line-number" data-line-number="61"></td>
        <td id="LC61" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 7 - Replacing : assertNotNull(actual) .......................... by : assertThat(actual).isNotNull()<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L62" class="blob-num js-line-number" data-line-number="62"></td>
        <td id="LC62" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertNotNull(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isNotNull()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L63" class="blob-num js-line-number" data-line-number="63"></td>
        <td id="LC63" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertNotNull([[:blank:]]*\(.*\))/assertThat(\1).isNotNull()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L64" class="blob-num js-line-number" data-line-number="64"></td>
        <td id="LC64" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L65" class="blob-num js-line-number" data-line-number="65"></td>
        <td id="LC65" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 8 - Replacing : assertTrue(logicalCondition) ................... by : assertThat(logicalCondition).isTrue()<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L66" class="blob-num js-line-number" data-line-number="66"></td>
        <td id="LC66" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertTrue(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isTrue()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L67" class="blob-num js-line-number" data-line-number="67"></td>
        <td id="LC67" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertTrue([[:blank:]]*\(.*\))/assertThat(\1).isTrue()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L68" class="blob-num js-line-number" data-line-number="68"></td>
        <td id="LC68" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L69" class="blob-num js-line-number" data-line-number="69"></td>
        <td id="LC69" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span> 9 - Replacing : assertFalse(logicalCondition) .................. by : assertThat(logicalCondition).isFalse()<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L70" class="blob-num js-line-number" data-line-number="70"></td>
        <td id="LC70" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertFalse(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\))/assertThat(\2).as(\1).isFalse()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L71" class="blob-num js-line-number" data-line-number="71"></td>
        <td id="LC71" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertFalse([[:blank:]]*\(.*\))/assertThat(\1).isFalse()/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L72" class="blob-num js-line-number" data-line-number="72"></td>
        <td id="LC72" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L73" class="blob-num js-line-number" data-line-number="73"></td>
        <td id="LC73" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span>10 - Replacing : assertSame(expected, actual) ................... by : assertThat(actual).isSameAs(expected)<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L74" class="blob-num js-line-number" data-line-number="74"></td>
        <td id="LC74" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertSame(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isSameAs(\2)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L75" class="blob-num js-line-number" data-line-number="75"></td>
        <td id="LC75" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertSame([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isSameAs(\1)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L76" class="blob-num js-line-number" data-line-number="76"></td>
        <td id="LC76" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L77" class="blob-num js-line-number" data-line-number="77"></td>
        <td id="LC77" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span>11 - Replacing : assertNotSame(expected, actual) ................ by : assertThat(actual).isNotSameAs(expected)<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L78" class="blob-num js-line-number" data-line-number="78"></td>
        <td id="LC78" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertNotSame(\(\&quot;.*\&quot;\),[[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\3).as(\1).isNotSameAs(\2)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L79" class="blob-num js-line-number" data-line-number="79"></td>
        <td id="LC79" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/assertNotSame([[:blank:]]*\(.*\),[[:blank:]]*\(.*\))/assertThat(\2).isNotSameAs(\1)/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L80" class="blob-num js-line-number" data-line-number="80"></td>
        <td id="LC80" class="blob-code js-file-line">
</td>
      </tr>
      <tr>
        <td id="L81" class="blob-num js-line-number" data-line-number="81"></td>
        <td id="LC81" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span><span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L82" class="blob-num js-line-number" data-line-number="82"></td>
        <td id="LC82" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span>12 - Replacing JUnit static import by AssertJ ones, at this point you will probably need to :<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L83" class="blob-num js-line-number" data-line-number="83"></td>
        <td id="LC83" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span>12 --- optimize imports with your IDE to remove unused imports<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L84" class="blob-num js-line-number" data-line-number="84"></td>
        <td id="LC84" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span>12 --- add &quot;import static org.assertj.core.api.Assertions.within;&quot; if you were using JUnit number assertions with delta<span class="pl-pds">&#39;</span></span></td>
      </tr>
      <tr>
        <td id="L85" class="blob-num js-line-number" data-line-number="85"></td>
        <td id="LC85" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/import static org.junit.Assert.assertEquals;/import static org.assertj.core.api.Assertions.assertThat;/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L86" class="blob-num js-line-number" data-line-number="86"></td>
        <td id="LC86" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/import static org.junit.Assert.fail;/import static org.assertj.core.api.Assertions.fail;/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L87" class="blob-num js-line-number" data-line-number="87"></td>
        <td id="LC87" class="blob-code js-file-line">find <span class="pl-c1">.</span> -name <span class="pl-s"><span class="pl-pds">&quot;</span><span class="pl-smi">$FILES_PATTERN</span><span class="pl-pds">&quot;</span></span> -exec sed <span class="pl-smi">${SED_OPTIONS}</span> <span class="pl-s"><span class="pl-pds">&#39;</span>s/import static org.junit.Assert.\*;/import static org.assertj.core.api.Assertions.*;/g<span class="pl-pds">&#39;</span></span> <span class="pl-s"><span class="pl-pds">&#39;</span>{}<span class="pl-pds">&#39;</span></span> <span class="pl-cce">\;</span></td>
      </tr>
      <tr>
        <td id="L88" class="blob-num js-line-number" data-line-number="88"></td>
        <td id="LC88" class="blob-code js-file-line"><span class="pl-c1">echo</span> <span class="pl-s"><span class="pl-pds">&#39;</span><span class="pl-pds">&#39;</span></span></td>
      </tr>
</table>

  </div>

</div>

<a href="#jump-to-line" rel="facebox[.linejump]" data-hotkey="l" style="display:none">Jump to Line</a>
<div id="jump-to-line" style="display:none">
  <form accept-charset="UTF-8" action="" class="js-jump-to-line-form" method="get"><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /></div>
    <input class="linejump-input js-jump-to-line-field" type="text" placeholder="Jump to line&hellip;" autofocus>
    <button type="submit" class="btn">Go</button>
</form></div>

        </div>

      </div><!-- /.repo-container -->
      <div class="modal-backdrop"></div>
    </div><!-- /.container -->
  </div><!-- /.site -->


    </div><!-- /.wrapper -->

      <div class="container">
  <div class="site-footer" role="contentinfo">
    <ul class="site-footer-links right">
        <li><a href="https://status.github.com/" data-ga-click="Footer, go to status, text:status">Status</a></li>
      <li><a href="https://developer.github.com" data-ga-click="Footer, go to api, text:api">API</a></li>
      <li><a href="https://training.github.com" data-ga-click="Footer, go to training, text:training">Training</a></li>
      <li><a href="https://shop.github.com" data-ga-click="Footer, go to shop, text:shop">Shop</a></li>
        <li><a href="https://github.com/blog" data-ga-click="Footer, go to blog, text:blog">Blog</a></li>
        <li><a href="https://github.com/about" data-ga-click="Footer, go to about, text:about">About</a></li>

    </ul>

    <a href="https://github.com" aria-label="Homepage">
      <span class="mega-octicon octicon-mark-github" title="GitHub"></span>
</a>
    <ul class="site-footer-links">
      <li>&copy; 2015 <span title="0.06239s from github-fe142-cp1-prd.iad.github.net">GitHub</span>, Inc.</li>
        <li><a href="https://github.com/site/terms" data-ga-click="Footer, go to terms, text:terms">Terms</a></li>
        <li><a href="https://github.com/site/privacy" data-ga-click="Footer, go to privacy, text:privacy">Privacy</a></li>
        <li><a href="https://github.com/security" data-ga-click="Footer, go to security, text:security">Security</a></li>
        <li><a href="https://github.com/contact" data-ga-click="Footer, go to contact, text:contact">Contact</a></li>
    </ul>
  </div>
</div>


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-suggester-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="fullscreen-contents js-fullscreen-contents" placeholder=""></textarea>
      <div class="suggester-container">
        <div class="suggester fullscreen-suggester js-suggester js-navigation-container"></div>
      </div>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped tooltipped-w" aria-label="Exit Zen Mode">
      <span class="mega-octicon octicon-screen-normal"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped tooltipped-w"
      aria-label="Switch themes">
      <span class="octicon octicon-color-mode"></span>
    </a>
  </div>
</div>



    
    

    <div id="ajax-error-message" class="flash flash-error">
      <span class="octicon octicon-alert"></span>
      <a href="#" class="octicon octicon-x flash-close js-ajax-error-dismiss" aria-label="Dismiss error"></a>
      Something went wrong with that request. Please try again.
    </div>


      <script crossorigin="anonymous" src="https://assets-cdn.github.com/assets/frameworks-2c8ae50712a47d2b83d740cb875d55cdbbb3fdbccf303951cc6b7e63731e0c38.js"></script>
      <script async="async" crossorigin="anonymous" src="https://assets-cdn.github.com/assets/github-a612914f18d72984765c3aa8bf9dc71ff1da096692c5e14976f9910b2ec2fbda.js"></script>
      
      


  </body>
</html>

