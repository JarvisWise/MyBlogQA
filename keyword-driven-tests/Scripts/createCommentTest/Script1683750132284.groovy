import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:7001/simple-blog/')

WebUI.setText(findTestObject('Object Repository/Page_Login Page/input_Email_loginUserName'), 'test.mail@gmail.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login Page/input_Password_loginPassword'), 'teO7gFeCDasckeCIEoAHxg==')

WebUI.click(findTestObject('Object Repository/Page_Login Page/button_Login'))

WebUI.click(findTestObject('Object Repository/Page_Home Page/a_Create New Post'))

WebUI.setText(findTestObject('Object Repository/Page_Create Post/input_Post name_postName'), 'New Post Name For Test 1221')

WebUI.setText(findTestObject('Object Repository/Page_Create Post/textarea_Post Text_postText'), 'This is test post 1221')

WebUI.click(findTestObject('Object Repository/Page_Create Post/button_Create Post'))

WebUI.click(findTestObject('Object Repository/Page_Home Page/a_Show more'))

WebUI.setText(findTestObject('Object Repository/Page_Post Page/textarea_Test User_commentText'), 'This is Test Comment 1221')

WebUI.click(findTestObject('Object Repository/Page_Post Page/button_Create Comment'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Post Page/td_This is Test Comment 1221'), 0)

WebUI.click(findTestObject('Object Repository/Page_Post Page/a_Logout'))

WebUI.navigateToUrl('http://localhost:7001/simple-blog/edit/clean-test-user')

WebUI.closeBrowser()

