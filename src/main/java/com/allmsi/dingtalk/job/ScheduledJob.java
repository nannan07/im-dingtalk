package com.allmsi.dingtalk.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.allmsi.dingtalk.service.MailService;
import com.allmsi.dingtalk.service.ScheduledService;
import com.allmsi.sys.util.DateUtil;

@Component
public class ScheduledJob {

	@Resource
	private ScheduledService scheduledService;

	@Autowired
	private MailService mailService;

	@Scheduled(cron = "0 19 13 * * ?")
	public void scheduled() {
		String synDept = scheduledService.synDept();
		String synUser = scheduledService.synUser();
		String subject = "钉钉组织架构监控(" + DateUtil.dateConvertString(new Date()) + ")";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String messages = "新监控时间: " + sdf.format(new Date()) + "\n\n";
		String content = messages + "\n\n" +
				synDept + "\n" + synUser;
		new EmailThread(subject, content).run();
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
			mailService.sendSimpleMail(subject, content);
		}

	}
}
