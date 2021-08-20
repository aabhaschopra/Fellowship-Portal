from django.contrib import admin
from django.urls import path
from . import views

urlpatterns = [
	path('', views.home),
	path('login', views.postsignIn),
	path('logout', views.logout, name="log"),
	path('mentordetails', views.mentordetails),
	path('student_assignment', views.student_assignment),
	path('studentSessions', views.studentSessions),
	path('mentorSessions', views.mentorSessions),
	path('studentdetails', views.studentdetails),
	path('mentor_assignment', views.mentor_assignment),
	path('programmanager_assignment', views.programmanager_assignment),
	path('programmanagersessions', views.programmanagersessions),
	path('student_dashboard', views.student_dashboard),
]
