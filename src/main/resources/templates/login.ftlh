<#import "parts/common.ftlh" as c>
<#import "parts/log.ftlh" as l>

<@c.page>
Login page
<@l.login "/login" "in" />
    <a href="/registration" ><button class="btn btn-primary mt-3">Sign up</button></a>
</@c.page>
