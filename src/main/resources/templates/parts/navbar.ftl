<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <#if !isAdmin>
    <a class="navbar-brand" href="/login">Banner Viewer</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    </#if>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">
            <#if !isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
            </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/banner">Banners <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/category">Categories <span class="sr-only">(current)</span></a>
                </li>
            </#if>
        </ul>

        <#if isAdmin>
        <div class="navbar-text mr-3"><#if isAdmin>Admin</#if></div>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Sign Out</button>
        </form>
        </#if>
    </div>
</nav>