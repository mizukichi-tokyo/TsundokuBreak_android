# 変更した行以外は指摘しないように
github.dismiss_out_of_range_messages

# --------------------
# pr title
# --------------------
is_wip = github.pr_title.include? '[WIP]'

if is_wip
  warn('このPRは作業中です。')
end

is_to_develop = github.branch_for_base == 'develop'
is_start_with_issue_id = !!github.pr_title.match(/^#[0-9]+/)

if is_to_develop && !is_start_with_issue_id && !is_wip
  warn('PRのタイトルは<b>「#XXX Foo bar...」</b>とIssue idから始めてください。')
end

# --------------------
# diff size
# --------------------
is_big_pr = git.lines_of_code > 500

if is_big_pr
  warn('PRの変更量が多すぎます。可能であればPRを分割しましょう。分割が難しければ次から気をつけるようにしましょう。')
end

# ktlint
checkstyle_format.base_path = Dir.pwd
Dir["**/reports/ktlint/**.xml"].each do |file|
  checkstyle_format.report file
end

# android lint
android_lint.skip_gradle_task = true
android_lint.filtering = true
Dir["*/build/reports/lint-results*.xml"].each do |file|
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end
