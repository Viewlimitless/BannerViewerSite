<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    isAdmin = true
    >
<#else>
    <#assign
    isAdmin=false
    >

</#if>