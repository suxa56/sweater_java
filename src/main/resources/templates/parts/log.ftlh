<#macro login path act>
    <form action="${path}" method="post" class="mt-3">
        <div class="mb-3 col-4">
            <label for="loginName" class="form-label"> User name  </label>
            <input class="form-control" id="loginName" type="text" name="username" placeholder="Username"/>
        </div>
        <div class="mb-3 col-4">
            <label for="loginPass" class="form-label"> Password </label>
            <input class="form-control" id="loginPass" type="password" name="password" placeholder="Password"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-success">Sign ${act}</button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post" class="ms-2 col-1">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-primary"> Sign out </button>
    </form>
</#macro>