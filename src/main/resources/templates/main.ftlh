<#import "parts/common.ftlh" as c>
<#import "parts/log.ftlh" as l>

<@c.page>
    <form method="get" action="/main" class="row mb-3 g-3">
        <div class="col-auto">
            <input type="text" name="filter" class="form-control" value="${RequestParameters.filter!}"
                   placeholder="Searchc">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

    <a class="btn btn-primary mb-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collaspse my-3 <#if message??>show</#if>" id="collapseExample">
        <form method="post" enctype="multipart/form-data" class="row g-3">
            <div class="col-auto">
                <input class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>" type="text" name="text" placeholder="Message">
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="col-auto">
                <input class="form-control  ${(tagError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.tag}</#if>"type="text" name="tag" placeholder="Tag">
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>

            </div>
            <div class="col-auto">
                <input class="form-control" type="file" name="file" id="formFile">
            </div>
            <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="col-auto">
                <button class="btn btn-primary" type="submit">Add</button>

            </div>
        </form>
    </div>


    <div class="row row-cols-1 row-cols-md-3 g-3">
        <#list messages as message>
            <div class="col">
            <div class="card my-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" alt="" class="card-img-top">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
            </div>
        <#else>
            <div class="mt-3 me-2">
            No messages

            </div>
        </#list>
    </div>
</@c.page>