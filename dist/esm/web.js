var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { WebPlugin } from '@capacitor/core';
export class RingfencePluginWeb extends WebPlugin {
    constructor() {
        super({
            name: 'RingfencePlugin',
            platforms: ['web'],
        });
        /*
          async getContacts(filter:String): Promise<{results: any[] }> {
            console.log('filter: ', filter);
            return {
              results: [{
                firstName: 'Dummy',
                lastName: 'Entry',
                telephone: '123456'
              }]
            }
          }
          */
        this.jsonURL = "https://api.jsonbin.io/v3/b/600033fb68f9f835a3df124a/latest>";
    }
    echo(options) {
        return __awaiter(this, void 0, void 0, function* () {
            console.log('ECHO', options);
            return options;
        });
    }
    enableUserLocation() {
        return __awaiter(this, void 0, void 0, function* () { });
    }
    passJson(jsonString) {
        return __awaiter(this, void 0, void 0, function* () {
            console.log(jsonString);
        });
    }
}
const RingfencePlugin = new RingfencePluginWeb();
export { RingfencePlugin };
import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RingfencePlugin);
//# sourceMappingURL=web.js.map