<#import "parts/common.ftlh" as c>
<#import "parts/log.ftlh" as l>

<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert danger-alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message>
        <div class="alert danger-${messageType}">
            ${message}
        </div>
    </#if>
<@l.login "/login" false />
</@c.page>
