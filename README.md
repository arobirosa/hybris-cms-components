# hybris-cms-components

Playground to test WCMS and SmartEdit's features and show examples of CMS components.

# Requirements
* SAP JDK 64-Bit 11.0.6
* Intellij IDEA 2019.1 Ultimate Edition
    * Impex plugin 
    * QA Plugins
* Git 2.7.4
   * Git Flow 1.6.1 (AVH Edition)
* SAP hybris 1905
* All running on XUbuntu 16.04.6 LTS or other Unix-like operating system
* PMD 5.5.7 (automatically installed by Ant)
* Checkstyle 8.14 (automatically installed by Ant)
* FindBugs 3.0.1  (automatically installed by Ant)
* Cobertura 2.1.1 (automatically installed by Ant)

# Development Environment Set-up

Here you will find how the set up the project to start developing. I assume that you are working on linux.

## Dev Database
The hsql which comes with Hybris is enough to run and test the deployment script manager. No other database is required.

Set up of the development environment
* Create the parent directory with
```
cd ~/no_backup
```
* Create a local git repository using:
```
git clone git@github.com:arobirosa/hybris-cms-components.git hybris-cms-components
```
* Open the file hybris-cms-components/.git/config and add the **git flow configuration**:
```
[gitflow "branch"]
	master = production
	develop = master
[gitflow "prefix"]
	feature = feature/
	release = release/
	hotfix = hotfix/
	support = support/
	versiontag = 
```
* Due to a bug in git flow, checkout the branch production locally:
```
git checkout production 
```
If you don't do this, git flow will complain saying it is configured.

* Download the hybris SAP platform.
* Extract the zip file **hybris-cms-components**:
```
unzip hybris-commerce-suite-19.05.00.10.zip -d ~/no_backup/areco/hybris-cms-components
```
* Go to the directory hybris-commerce-suite-19.05.00.10.zip and run in a console:
```
cd installer && ./install.sh -A local_property:initialpassword.admin=nimda -r b2c_acc_plus initialize
. ./setantenv.sh
ant all
```
This will create the config folder, set up the extensios for the b2c accelerator and import the apparel and electronics store. For windows you have to run instead the setantenv.bat file. Type the command **setantenv.bat**

* After this, please follow these steps [https://help.sap.com/viewer/4c33bf189ab9409e84e589295c36d96e/1905/en-US/8ace75c786691014a5e9dcafa29d5410.html](Customizing the Accelerator with extgen and modulegen) to create the training storefrontend.
* Add the domain electronics.local to your local /etc/hosts file:
``127.0.0.1       electronics.local`` 

* Add the content of hybris-cms-components/hybris/config/local.properties.template to your own local.properties:
```
cat hybris/config/local.properties.template >> hybris/config/local.properties
```

* Add the following extensions to hybris/config/localextensions.xml:
```
    <extension name="areconewslettercomponent"/>
```
* Add the addon ``ant addoninstall -Daddonnames="areconewslettercomponent" -DaddonStorefront.yacceleratorstorefront="trainingstorefront"``
* Now you are ready to compile everything. Run in a console directly in the path of the project directory **hybris-cms-components**:
```
ant clean all qa
```

If you experience memory issues during the compile process, check the option **standalone.javaoptions** in the file hybris\bin\platform\resources\advanced.properties by reducing the memory configured.

This will compile all the Hybris extensions, the deployment manager, install PMD, Findbugs, Checkstyle and Cobertura, run all the tests and check for QA violations.

* Import the project into Intellij IDEA.

# Versioning
[Semantic Versioning 2.0.0](http://semver.org/) is used.

# Notes about the code
* The attributes are only localized in English
* The NewsletterSignUpCMSComponent don't store the email in the database or send it to an external system. You have to implement this step yourself
* The permissions for the CMS components aren't configured. You can do this with a Areco Deployment Script
* Because the CMS components are usually edited with SmartEdit, they don't have any backoffice configuration
* Because the test data of the stores is imported in the "Create Project" step, Areco Deployment Manager also runs in this step.  
