package de.eonaminecraft.eonachat;


/**
 * A Message
 */
class EonaMessage
{

private String from;
private String message;
private String to;


EonaMessage(String from, String message, String to)
{
	this.from = from;
	this.message = message;
	this.to = to;
	EonaChat.log("New Message: " + from + " to " + to + " | " + message);
}


String getFrom()
{
	return from;
}


String getMessage()
{
	return message;
}


String getTo()
{
	return to;
}
}
