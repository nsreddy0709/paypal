<html>
<head>

</head>
<body>
<h1>Welcome to paypal payment ${uname}</h1>
<h2>Total amount ${amount}</h2>

<form action="/pay" action="post">
    <input type="hidden" name="amount" value=${amount}>
    <input type="hidden" name="uname" value=${uname}>
    <input type="submit" value="pay now">
</form>

</body>
</html>