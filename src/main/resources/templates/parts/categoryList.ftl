<div class="col-3 mt-4">
    <div class="card" style="width: 18rem;">
        <div class="col2 ml-3 mt-2 text-center">
            <b>Categories:</b>
        </div>
        <form action="/category" method="get">
            <div class="form-row">
                <div class="col2 ml-3 mt-2">
                    <input type="text" name="filter" class="form-control col" value="${filter}"
                           placeholder="Enter category name">
                </div>
                <div class="col text-right mr-2">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary my-2">Find</button>
                </div>
            </div>
        </form>
        <ul class="list-group list-group-flush">
            <#list categories as category>
                <li class="list-group-item">
                    <a href="/category/${category.id}">${category.name}</a>
                </li>
            <#else>
                <li class="list-group-item">
                    No categories
                </li>
            </#list>
            <div class="text-center">
                <form action="/category/add" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary btn-lg btn-block">Create new Category</button>
                </form>
            </div>
        </ul>
    </div>
</div>