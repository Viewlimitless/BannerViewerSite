<div class="col-3 mt-4">
    <div class="card" style="width: 18rem;">

        <div class="col2 ml-3 mt-2 text-center">
            <b>Banners:</b>
        </div>
        <form action="/banner" method="get">
            <div class="form-row">
                <div class="col2 ml-3 mt-2">
                    <input type="text" name="filter" value="${filter}" class="form-control col"
                           placeholder="Enter banner name">
                </div>
                <div class="col text-right mr-2">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary my-2">Find</button>
                </div>
            </div>
        </form>
        <ul class="list-group list-group-flush">
            <#list banners as banner>
                <li class="list-group-item">
                    <a href="/banner/${banner.id}">${banner.name}</a>
                </li>
            <#else>
                <li class="list-group-item">
                    No banners
                </li>
            </#list>

            <div class="text-center">
                <form action="/banner/add" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary btn-lg btn-block">Create new Banner</button>
                </form>
            </div>
        </ul>
    </div>
</div>