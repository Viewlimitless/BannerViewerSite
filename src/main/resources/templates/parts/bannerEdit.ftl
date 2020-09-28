<div class="col-8 mt-4">
    <div class="card ">
        <div class="card-header">
            <div class="form-group row mt-3 ml-3 ">
                ${editorTitle}
            </div>
        </div>
        <div class="card-body ">
            <form action="/banner" method="post">
                <input type="hidden" name="_method" value="put"/>
                <div class="form-group row ">
                    <label class="col-sm-2 col-form-label">Name</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="bannerName" value="${banner.name}"
                               placeholder="Enter banner's name">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Price</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" name="price" value="${banner.price}"
                               placeholder="Enter banner's price">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Category</label>
                    <div class="col-sm-5">
                        <select class="custom-select" name="categorySelected">
                            <option value="${banner.category.id}">${banner.category.name}</option>
                            <#list categories as category>
                                <#if banner.category.id!=category.id>
                                    <option value="${category.id}">${category.name}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Text</label>


                    <div class="input-group col-sm-5">
                        <textarea class="form-control" aria-label="With textarea" name="content"
                                  value="${banner.content}"
                                  placeholder="${banner.content}"></textarea>
                    </div>
                </div>
                <div class="form-group row" style="height: 100px"></div>
                <#if allertMessage??>
                    <div class="form-group row">
                        <div class="alert alert-danger mx-auto" style="width: 900px" ; role="alert">
                            ${allertMessage}
                        </div>
                    </div>
                </#if>


                <div class="form-group row">
                    <div class="col">
                        <input type="hidden" value="${banner.id}" name="bannerId">
                        <input type="hidden" value="${_csrf.token}" name="_csrf">
                        <button type="submit" class="btn btn-primary mt-2 mr-3">Save</button>
            </form>
        </div>

        <div class="text-right">
            <form action="/banner" method="post">
                <input type="hidden" name="_method" value="delete"/>
                <input type="hidden" value="${banner.id}" name="bannerId">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <button type="submit" class="btn btn-primary mt-2 mr-3 bg-danger">Delete</button>
            </form>

        </div>
    </div>
</div>
</div>
</div>