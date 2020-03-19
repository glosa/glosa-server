(defproject glosa "0.1.0"
  :description "glosa"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [;; Clojure
                 [org.clojure/clojure "1.10.1"]
                 ;; Tadam core
                 [tadam-core "0.3.4"]
                 ;; Managing environment settings
                 [environ "1.1.0"]
                 ;; HTTP Server
                 [ring "1.7.1"]
                 ;; Ring middleware that prevents CSRF attacks
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-anti-forgery "1.3.0"]
                 ;; Routing
                 [compojure "1.6.1"]
                 ;; Templates
                 [selmer "1.12.12"]
                 ;; Validations
                 [jkkramer/verily "0.6.0"]
                 ;; JSON encoding
                 [cheshire "5.9.0"]
                 ;; Yaml
                 [clj-yaml "0.4.0"]]
  :plugins [;; DEV TOOLS
            [lein-environ "1.1.0" :hooks false]
            ;;; Check idiomatic bug
            [lein-kibit "0.1.7"]
            ;;; Check format
            [lein-cljfmt "0.6.4"]
            ;;; Generate documentation
            [lein-codox "0.10.7"]]
  ;; Map configuration for Ring
  :ring {:handler glosa.core.wrapped-handler}
  ;; ALIAS
  :aliases {"check-idiomatic" ["kibit" "src"]
            "check-format"    ["cljfmt" "check"]}
  ;; LEIN
  :main ^:skip-aot glosa.core
  :aot  [glosa.core]
  :repl-options {:init-ns glosa.core})
