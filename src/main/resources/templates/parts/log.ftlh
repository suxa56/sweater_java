<#macro login path isRegisterForm>
    <form action="${path}" method="post" class="mt-3">
        <div class="mb-3 col-4">
            <label for="loginName" class="form-label"> User name </label>
            <input class="form-control" id="loginName" type="text" name="username" placeholder="Username"/>
        </div>
        <div class="mb-3 col-4">
            <label for="loginPass" class="form-label"> Password </label>
            <input class="form-control" id="loginPass" type="password" name="password" placeholder="Password"/>
        </div>
        <#if isRegisterForm>
            <div class="mb-3 col-4">
                <label for="loginEmail" class="form-label"> Email </label>
                <input class="form-control" id="loginEmail" type="email" name="email"
                       placeholder="example@example.com"/>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <#if !isRegisterForm>
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group">
                    <button type="submit" class="btn btn-primary me-2">Sign in</button>
                </div>
                <div class="btn-group">
                    <a href="/registration" class="btn btn-success">Sign up</a>
                </div>
            </div>
        <#else >
            <button type="submit" class="btn btn-success">Sign up</button>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post" class="ms-2 col-1">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary"> Sign out</button>
    </form>
</#macro>