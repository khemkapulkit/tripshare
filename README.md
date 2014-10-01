Date : 09/29/2014

This is the root readme file for this project. 

Authors:
		Nishant Garg
		Pulkit Khemka
		Nikhil G Rao
		Gautham Suriya M
		Mukul Kulkarni
Git Reference:

Workflow :
	Pull latest changes before working on the repository.
	Work on code, make changes.
	Pull latest changes again to make sure there is no conflict.
	Add changes to the stage.
	commit changes
	push changes

Update your repository:
	git pull -u

Push changes to Github:
Since you are already in this repository, remote is already setup.
You can directly run following on the command line:
	git push origin master -u

Committing changes:

Everytime you add a new file or make changes to existing file, you had to add
files to the stage using following command :
	git add filename1 filename2
You can add everychange in one go, this add every filechange and new file
to the stage:
	git add.
Then go and commit :
	git commit -m "message"

