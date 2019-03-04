package com.allmsi.dingtalk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.dingtalk.service.MailService;
import com.allmsi.dingtalk.service.ScheduledService;
import com.allmsi.sys.util.DateUtil;

@Controller
public class ScheduledController {

	@Resource
	private ScheduledService scheduledService;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/syn", method = RequestMethod.GET)
	@ResponseBody
	public String syn() {
		String synDept = scheduledService.synDept();
		String synUser = scheduledService.synUser();
		String subject = "钉钉组织架构监控(" + DateUtil.dateConvertString(new Date()) + ")";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String messages = "新监控时间: " + sdf.format(new Date()) + "\n\n";
		String content = messages + "\n" + synDept + "\n" + synUser;
		System.out.println("content---"+content);
		mailService.sendSimpleMail(subject, content);
		return content;
	}

	class EmailThread implements Runnable {

		private String subject;

		private String content;

		public EmailThread(String subject, String content) {
			this.subject = subject;
			this.content = content;
		}

		@Override
		public void run() {
			System.out.println("111111");
			mailService.sendSimpleMail(subject, content);
		}

	}
}
