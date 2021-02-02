<#import "parts/common.ftlh" as c>
<#import "parts/log.ftlh" as l>

<@c.page>
<body>
Add new user
${message!""}
<@l.login "/registration" true/>
</body>
</@c.page>
