(ns trend.cli.core
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [trend.transcript.interface :as transcript]
            [trend.tenant.interface :as tenant]
            [trend.database.interface :as database])
  (:gen-class))

(def cli-options
  [["-f" "--file NAME" "File name to read"
    :validate [string? "Must be a string"]]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> [""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  ingest   Injest a transcript"
        "  quit     Quit cli"
        ""]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with an error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}
      errors ; errors => exit with description of errors
      {:exit-message (error-msg errors)}
      ;; custom validation on arguments
      (and (= 1 (count arguments))
           (#{"ingest" "quit"} (first arguments)))
      {:action (first arguments) :options options}
      :else ; failed custom validation => exit with usage summary
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [action options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (let [db (database/start)
            tenant (or (tenant/find-first! {:db db})
                       (tenant/create! {:db db} "Default"))
            ctx {:db db :tenant-id (:id tenant)}]
        (case action
          "ingest" (transcript/ingest-file ctx :foo (:file options))
          "quit" (exit 0 "Bye!"))))))
