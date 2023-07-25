from django.shortcuts import render
from pyrebase import pyrebase
from django.contrib import messages
# Create your views here.

config={
	"apiKey": "AIzaSyCgMO6h3zdhTvnbdgTBok3nnxlFIxtWzFY",
    "authDomain": "cfg-team-56.firebaseapp.com",
    "databaseURL": "https://cfg-team-56-default-rtdb.firebaseio.com",
    "projectId": "cfg-team-56",
    "storageBucket": "cfg-team-56.appspot.com",
    "messagingSenderId": "366000995858",
    "appId": "1:366000995858:web:c34778b5f72cf2a38ce2cb",
    "measurementId": "G-W5F7EDPKPK"
}
# Initialising database,auth and firebase for further use
firebase=pyrebase.initialize_app(config)
authe = firebase.auth()
db=firebase.database()
storage = firebase.storage()

def home(request):
	return render(request, "cfg/index.html")

def postsignIn(request):
	email=request.POST.get('email')
	pasw=request.POST.get('password')    
	try:
			#messages.error(request, "if there is no error then signin the user with given email and password")
		user = authe.sign_in_with_email_and_password(email,pasw)        
		
	except:
		messages.success(request, "Invalid Credentials!!Please ChecK your Data")
		return render(request,"cfg/loginpage.html")

	return dashboard(request,email)	
	


def logout(request):
	try:
		del request.session['uid']
	except:
		pass
	return render(request,"cfg/loginpage.html")

def dashboard(request,email):
    if email=="program.manager@laali.com":
        return render(request,"cfg/programmanager_dashboard.html")        
                            
    elif email=="mentor@laali.com":
        return render(request,"cfg/mentor_dashboard.html")

    else:
    	return render(request,"cfg/student_dashboard.html")

def mentordetails(request):
	all_users = db.child("ProgramManager").child("Mentor").get()
	users = []
	name = all_users.val()
	for detail in name:
		if detail == 'name':
			users.append(name['name'])

	print(users)
	return render(request, "cfg/mentordetails.html", {"users": users})
	#return render(request, "cfg/mentordetails.html")


def student_assignment(request):
	return render(request, "cfg/student_assignment.html")

def mentor_assignment(request):
	return render(request, "cfg/mentor_assignment.html")

def programmanager_assignment(request):
	return render(request, "cfg/programmanager_assignment.html")

def studentSessions(request):
	return render(request, "cfg/studentSessions.html")

def mentorSessions(request):
	return render(request, "cfg/mentorSessions.html")

def programmanagersessions(request):
	return render(request, "cfg/programmanagersessions.html")

def student_dashboard(request):
	return render(request, "cfg/student_dashboard.html")

def studentdetails(request):
	all_users = db.child("ProgramManager").child("Mentor").child("Students").get()
	users=[]
	assign=[]
	n=all_users.val()
	for students in n:
		#x=n[students]['name']
		users.append(n[students]['name'])
		#assign.append(n[students]['number of assignments'])
		#print(x)
		#print(x)
		
    	#print(students.age())    	
	return render(request, "cfg/studentdetails.html", {"users":users})

def upload_as(request):
	storage.child("images/example.jpg").put("example2.jpg")