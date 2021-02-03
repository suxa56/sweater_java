<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>${username}</h5>
    ${message!""}
    <form method="post" class="mt-3">
        <div class="mb-3 col-4">
            <label for="loginPass" class="form-label"> Password </label>
            <input class="form-control" id="loginPass" type="password" name="password" placeholder="Password"/>
        </div>
        <div class="mb-3 col-4">
            <label for="loginEmail" class="form-label"> Email </label>
            <input class="form-control" id="loginEmail" type="email" name="email"
                   placeholder="example@example.com" value="${email!''}"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-success">Save</button>
    </form>

</@c.page>
