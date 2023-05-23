package Pojoclass;

import java.util.List;

public class course {
private List<webAutomation>WebAutomation;
public List<webAutomation> getWebAutomation() {
	return WebAutomation;
}
public void setWebAutomation(List<webAutomation> webAutomation) {
	WebAutomation = webAutomation;
}
public List<API> getApi() {
	return Api;
}
public void setApi(List<API> api) {
	Api = api;
}
public List<mobile> getMobile() {
	return Mobile;
}
public void setMobile(List<mobile> mobile) {
	Mobile = mobile;
}
private List<API> Api;
private List<mobile> Mobile;
}
