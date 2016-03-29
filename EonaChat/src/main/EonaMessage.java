package main;


import java.util.Set;


/**
 * A Message
 */
class EonaMessage
{

private String from = "";
private String message = "";
private Set<String> to;


EonaMessage(String from, String message, Set<String> to)
{
	this.from = from;
	this.message = message;
	this.to = to;
}


String getFrom()
{
	return from;
}


void setFrom(String from)
{
	this.from = from;
}


String getMessage()
{
	return message;
}


void setMessage(String message)
{
	this.message = message;
}


Set<String> getTo()
{
	return to;
}
}
