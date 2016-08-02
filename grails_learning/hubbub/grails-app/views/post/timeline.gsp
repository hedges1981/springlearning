<html>
<head>
    <title>
        Timeline for ${ user.profile ? user.profile.fullName :
            user.loginId }
    </title>
    <meta name="layout" content="main"/>
    <g:javascript library="jquery"/>  <!-- NOTE:-->
    <g:javascript>
        function clearPost(e) {
            $('#postContent').val('');
        }
        function showSpinner(visible) {
            if (visible) $('#spinner').show();
            else $('#spinner').hide();
        }
        function addTinyUrl(data) {
            var tinyUrl = data.urls.small;
            var postBox = $("#postContent")
            postBox.val(postBox.val() + tinyUrl);
            toggleTinyUrl();
            $("#tinyUrl input[name='fullUrl']").val('');
        }
    </g:javascript>
</head>
<body>
<h1>Timeline for ${ user.profile ? user.profile.fullName :
    user.loginId }</h1>

<!--NOTE HERE the conditional for showing the flash message, see the controller for where it be set-->
<g:if test="${flash.message}">
    <div class="flash">
        ${flash.message}
    </div>
</g:if>


<div id="newPost">
    <h3>
        What is ${user.profile.fullName} hacking on right now?
    </h3>
    <p>
        <!--- NOTE: old form before we made an ajax one -->
        %{--<g:form action="addPost" id="${params.id}">--}%
            %{--<g:textArea id='postContent' name="content"--}%
                        %{--rows="3" cols="50"/><br/>--}%
            %{--<g:submitButton name="post" value="Post"/>--}%
        %{--</g:form>--}%

        <!-- NOTE: new form that uses ajax -->
        <g:form>
            <g:textArea id="postContent" name="content" rows="3" cols="50"/><br/>
            <!-- NOTE: nice stuff in here, see the url and how it calls javascript functions -->
            <!-- NOTE: the update is important, as this tells it what div gets updated after it comes back -->
            <g:submitToRemote value="Post"
                              url="[controller: 'post', action: 'addPostAjax']"
                              update="allPosts"
                              onSuccess="clearPost(data)"
                              onLoading="showSpinner(true)"
                              onComplete="showSpinner(false)"/>
            <g:img id="spinner" style="display: none"
                   uri="/images/spinner.gif"/>
        </g:form>

    <div id="demoOfProcessingJson" >
        <g:formRemote name="tinyUrlForm" url="[action: 'getSomeJson']"
                      onSuccess="alert(data.urls.small);">
            TinyUrl: <g:textField name="fullUrl"/>
            <g:submitButton name="submit" value="Make Tiny"/>
        </g:formRemote>
    </div>
    </p>
</div>



<div id="allPosts">
    <g:each in="${user.posts}" var="post">
        <div class="postEntry">
            <div class="postText">
                ${post.content}
            </div>
            <div class="postDate">
                ${post.dateCreated}
            </div>
        </div>
    </g:each>
</div>
</body>
</html>