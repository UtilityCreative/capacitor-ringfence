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
        let result = call.getString("jsonPassed") ?? ""
        var propertiesParsed: JsonPassed! = nil
        
        do {
            if let data = result.data(using: String.Encoding.utf8){
                propertiesParsed = try decodeGeofenceJson(data:data)
                if let locations:[Record] = propertiesParsed.geofenceData?.record {
                    print(locations[0].lat as Any)
                    // geofence data is passed from ionic app to here
                    setupGeolocations(locations: locations)
                }
            }
            
        }
        catch let DecodingError.dataCorrupted(context) {
            print(context)
        } catch let DecodingError.keyNotFound(key, context) {
            print("Key '\(key)' not found:", context.debugDescription)
            print("codingPath:", context.codingPath)
        } catch let DecodingError.valueNotFound(value, context) {
            print("Value '\(value)' not found:", context.debugDescription)
            print("codingPath:", context.codingPath)
        } catch let DecodingError.typeMismatch(type, context)  {
            print("Type '\(type)' mismatch:", context.debugDescription)
            print("codingPath:", context.codingPath)
        } catch {
            print("error: ", error)
        }

    }
    
    func decodeGeofenceJson(data:Data) throws -> JsonPassed {
        guard let geofencesParsed = try JSONDecoder().decode(JsonPassed?.self, from: data) else { throw ValidationError.typeMismatch
        }
        return geofencesParsed
    }
    /*
    @objc override public func load() {
        // On load do the geolocation setup
        setupGeolocations()
    }
    */
    
    
    func setupGeolocations(locations:[Record]) {
        // Make sure the devices supports region monitoring.
        if CLLocationManager.isMonitoringAvailable(for: CLCircularRegion.self) {
            
            // Called when the plugin is first constructed in the bridge
            requestPermissionNotifications()
                    
            // Do any additional setup after loading the view
            
            locationManager.delegate = self
            
            locationManager.requestAlwaysAuthorization()
            
            locationManager.startUpdatingLocation()
            
            locationManager.distanceFilter = 100
           
            for (index, location) in locations.enumerated(){
                if index < 21 {
                    let geoFenceRegion:CLCircularRegion = CLCircularRegion(center: CLLocationCoordinate2DMake((location.lat! as NSString).doubleValue ,  (location.long! as NSString).doubleValue), radius: ((location.radius! as NSString).doubleValue), identifier: (location.name)!)
                    geoFenceRegion.notifyOnEntry = true
                    geoFenceRegion.notifyOnExit = false
                    locationManager.startMonitoring(for: geoFenceRegion)
                }
            }

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
            content.body = "Be Wise - look after your mates"
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


enum ValidationError: LocalizedError {
    case inValidValue
    case typeMismatch
}

class JsonPassed: Codable {
    let geofenceData: GeofenceData?
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        if let geofenceData = try container.decodeIfPresent(GeofenceData.self, forKey: .geofenceData) { self.geofenceData = geofenceData } else { self.geofenceData = nil}
    }
}

class GeofenceData: Codable {
    let record: [Record]?
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        if let record = try container.decodeIfPresent([Record].self, forKey: .record) { self.record = record } else { self.record = []}
    }
}

class Record: Codable {
    let name:String?
    let lat:String?
    let long:String?
    let radius:String?
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        if let name = try container.decodeIfPresent(String.self, forKey: .name) { self.name = name } else { self.name = ""}
        if let lat = try container.decodeIfPresent(String.self, forKey: .lat) { self.lat = lat } else { self.lat = ""}
        if let long = try container.decodeIfPresent(String.self, forKey: .long) { self.long = long } else { self.long = ""}
        if let radius = try container.decodeIfPresent(String.self, forKey: .radius) { self.radius = radius } else { self.radius = ""}
    }
}
