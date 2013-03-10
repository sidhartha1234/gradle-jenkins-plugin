package com.terrafolio.gradle.plugins.jenkins

import org.gradle.api.Plugin
import org.gradle.api.Project

class JenkinsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		applyTasks(project)
		applyConventions(project)
	}
	
	def applyTasks(Project project) {
		project.task('updateJenkinsJobs', type: UpdateJenkinsJobsTask)
		project.task('deleteJenkinsJobs', type: DeleteAllJenkinsJobsTask)
		project.task('dumpJenkinsJobs', type: DumpJenkinsJobsTask)
	}

	def applyConventions(Project project) {
		def jobs = project.container(JenkinsJob) { name ->
			new JenkinsJob(name)
		}
		
		def templates = project.container(JenkinsJobDefinition) { name ->
			new JenkinsJobDefinition(name)
		}
		
		def servers = project.container(JenkinsServerDefinition) { name ->
			new JenkinsServerDefinition(name)
		}
		
		def configuration = new JenkinsConfiguration(jobs, templates, servers)
		project.convention.plugins.jenkins = new JenkinsConfigurationConvention(configuration)
	}
}
