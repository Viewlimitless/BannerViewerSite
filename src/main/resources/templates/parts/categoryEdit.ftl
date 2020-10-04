<div class="col-8 mt-4">
    <div class="card ">
        <div class="card-header">
            <div class="form-group row mt-3 ml-3 ">
                ${editorTitle}
            </div>
        </div>
        <div class="card-body">
            <form action="/category" method="post">
                <input type="hidden" name="_method" value="put"/>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Name</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="categoryName" value="${category.name}"
                               placeholder="Enter category's name">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Request</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="request" value="${category.reqName}"
                               placeholder="Enter category's request">
                    </div>
                </div>
                <div class="form-group row" style="height: 230px"></div>
                <#if allertMessage??>
                    <div class="form-group row">
                        <div class="alert alert-danger mx-auto" style="width: 900px" role="alert">
                            ${allertMessage}
                        </div>
                    </div>
                </#if>
                <div class="form-group row">
                    <div class="col">
                        <input type="hidden" value="${category.id!-1}" name="categoryId">
                        <input type="hidden" value="${_csrf.token}" name="_csrf">
                        <button type="submit" class="btn btn-primary mt-2 mr-3">Save</button>
            </form>
        </div>
        <div class="text-right">
            <form action="/category" method="post">
                <input type="hidden" name="_method" value="delete"/>
                <input type="hidden" value="${category.id!-1}" name="categoryId">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <button type="submit" class="btn btn-primary mt-2 mr-3 bg-danger">Delete</button>
            </form>
        </div>
    </div>
</div>
</div>
</div>
