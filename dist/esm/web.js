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
    }
    enableUserLocation() {
        return __awaiter(this, void 0, void 0, function* () { });
    }
    // async passJson(jsonString:object) {
    passJson(_) {
        return __awaiter(this, void 0, void 0, function* () {
            // This web equivelant function isn't used (because geofence only works within native plugin) however it must
            // must remain here as an empty function because it is still called when in web context
        });
    }
}
const RingfencePlugin = new RingfencePluginWeb();
export { RingfencePlugin };
import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(RingfencePlugin);
//# sourceMappingURL=web.js.map