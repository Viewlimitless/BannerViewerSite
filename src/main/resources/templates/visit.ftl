<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>bannerview</title>
</head>
<body>
<div>
    <#if banner??>
        <div>
            ${banner.content}
        </div>
    <#else>
        Sorry, no content
    </#if>
</div>
</body>
</html>