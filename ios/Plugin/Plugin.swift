import Foundation
import Capacitor
import UIKit
import CoreLocation
import UserNotifications

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(RingfencePlugin)
public class RingfencePlugin: CAPPlugin, CLLocationManagerDelegate, UNUserNotificationCenterDelegate {

    let locationManager:CLLocationManager = CLLocationManager()
    
    
    @objc func passJson(_ call: CAPPluginCall) {
        let value = call.getString("jsonString") ?? ""
        print("***********************json string loaded*************************")
        print(value)
    }
    
    @objc override public func load() {
        // On load do the geolocation setup
        setupGeolocations()
    }
    
    
    
    func setupGeolocations() {
        // Make sure the devices supports region monitoring.
        if CLLocationManager.isMonitoringAvailable(for: CLCircularRegion.self) {
            
            // Called when the plugin is first constructed in the bridge
            requestPermissionNotifications()
                    
            // Do any additional setup after loading the view, typically from a nib.
            
            locationManager.delegate = self
            
            locationManager.requestAlwaysAuthorization()
            
            locationManager.startUpdatingLocation()
            
            locationManager.distanceFilter = 100
            
            let locations:[[Any]] = [
                [-37.82294808451516,144.9613830269109, "Bar 1"],
                [-37.82495985400438, 144.975384313801, "Bar 2"]
            ]
            
            for location in locations{
                let geoFenceRegion:CLCircularRegion = CLCircularRegion(center: CLLocationCoordinate2DMake(location[0] as! CLLocationDegrees, location[1] as! CLLocationDegrees), radius: 100, identifier: location[2] as! String)
                geoFenceRegion.notifyOnEntry = true
                geoFenceRegion.notifyOnExit = false
                locationManager.startMonitoring(for: geoFenceRegion)
            }

            print("Load location manager")
        }
    }
    
    
    
    public func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        for currentLocation in locations{
            print("\(String(describing: index)): \(currentLocation)")
            // "0: [locations]"
        }
    }
  
    public func locationManager(_ manager: CLLocationManager, didEnterRegion region: CLRegion) {
            print("Entered: \(region.identifier)")
            postLocalNotifications(eventTitle: "You have entered: \(region.identifier) - remember to look after your mates")
    }
        
    public func locationManager(_ manager: CLLocationManager, didExitRegion region: CLRegion) {
            print("Exited: \(region.identifier)")
            postLocalNotifications(eventTitle: "You have exited: \(region.identifier)")
    }
    
    
    
    
    public func requestPermissionNotifications(){
            let application =  UIApplication.shared
            
            if #available(iOS 10.0, *) {
                // For iOS 10 display notification (sent via APNS)
                UNUserNotificationCenter.current().delegate = self
                
                let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
                UNUserNotificationCenter.current().requestAuthorization(options: authOptions) { (isAuthorized, error) in
                    if( error != nil ){
                        print(error!)
                    }
                    else{
                        if( isAuthorized ){
                            print("authorized")
                            NotificationCenter.default.post(Notification(name: Notification.Name("AUTHORIZED")))
                        }
                        else{
                            let pushPreference = UserDefaults.standard.bool(forKey: "PREF_PUSH_NOTIFICATIONS")
                            if pushPreference == false {
                                let alert = UIAlertController(title: "Turn on Notifications", message: "Push notifications are turned off.", preferredStyle: .alert)
                                alert.addAction(UIAlertAction(title: "Turn on notifications", style: .default, handler: { (alertAction) in
                                    guard let settingsUrl = URL(string: UIApplication.openSettingsURLString) else {
                                        return
                                    }
                                    
                                    if UIApplication.shared.canOpenURL(settingsUrl) {
                                        UIApplication.shared.open(settingsUrl, completionHandler: { (success) in
                                            // Checking for setting is opened or not
                                            print("Setting is opened: \(success)")
                                        })
                                    }
                                    UserDefaults.standard.set(true, forKey: "PREF_PUSH_NOTIFICATIONS")
                                }))
                                alert.addAction(UIAlertAction(title: "No thanks.", style: .default, handler: { (actionAlert) in
                                    print("user denied")
                                    UserDefaults.standard.set(true, forKey: "PREF_PUSH_NOTIFICATIONS")
                                }))
                                let viewController = UIApplication.shared.keyWindow!.rootViewController
                                DispatchQueue.main.async {
                                    viewController?.present(alert, animated: true, completion: nil)
                                }
                            }
                        }
                    }
                }
            }
            else {
                let settings: UIUserNotificationSettings =
                    UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
                application.registerUserNotificationSettings(settings)
            }
        }

        
        
       public func postLocalNotifications(eventTitle:String){
            let center = UNUserNotificationCenter.current()
            
            let content = UNMutableNotificationContent()
            content.title = eventTitle
            content.body = "You've entered a new region"
        content.sound = UNNotificationSound.default
            
            let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 1, repeats: false)
            
            let notificationRequest:UNNotificationRequest = UNNotificationRequest(identifier: "Region", content: content, trigger: trigger)
            
            center.add(notificationRequest, withCompletionHandler: { (error) in
                if let error = error {
                    // Something went wrong
                    print(error)
                }
                else{
                    print("added")
                }
            })
        }
    
    
    var time: Date?
    @objc func refreshGeolocations() {
        time = Date()
        print("Time now is \(String(describing: time))")
    }
}



class ViewController: UIViewController{
    var time: Date?
    func refreshGeolocations() {
        time = Date()
        print("Time now is \(String(describing: time))")
    }
}

