<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">


<@c.page>
    <div class="col-11">
    <#if !isAdmin>
        <form action="/login" method="post">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> User Name : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="username" placeholder="User name"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> User Name : </label>
                <div class="col-sm-10">
                    <input type="password" name="password" class="form-control" placeholder="Password"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </div>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Sign In</button>
            </div>
        </form>
    <#else >
        <h5>Hello, Admin</h5>
        <div>Already loginned</div>
    </#if>
    </div>
</@c.page>