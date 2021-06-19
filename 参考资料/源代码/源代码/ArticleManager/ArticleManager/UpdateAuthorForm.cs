using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class UpdateAuthorForm : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        public string newname, newemail;
        public bool operationSucceed=false;
        public UpdateAuthorForm()
        {
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from Author", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "姓名";
            dataGridView1.Columns[2].HeaderCell.Value = "E-mail";
        }

        private void UpdateAuthorForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int editID = Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value);
            string editname = Convert.ToString(dataGridView1.CurrentRow.Cells[1].Value);
            string editemail = Convert.ToString(dataGridView1.CurrentRow.Cells[2].Value);
            UpdateAuthor_button.Enabled = false;
            Form cAF = new UpdatingAuthorForm(Convert.ToString(editID), editname,editemail);
            cAF.ShowDialog(this);
            UpdateAuthor_button.Enabled = true;
            if (!operationSucceed) return;
            string SqlCommandString = "update Author set name=@name,email=@email where authorid=" + Convert.ToString(editID);
            SqlCommand scmd = new SqlCommand(SqlCommandString, Sc);
            scmd.Parameters.Add(new SqlParameter("@name", SqlDbType.VarChar, 80, "name"));
            scmd.Parameters.Add(new SqlParameter("@email", SqlDbType.VarChar, 100, "email"));
            Sda.UpdateCommand = scmd;
            DataRow dr = Dt.Rows[dataGridView1.CurrentRow.Index];
            foreach (DataRow edr in Dt.Rows)
                if (Convert.ToInt32(edr["authorid"]) == editID)
                {
                    dr = edr;
                    break;
                }//if
            try
            {
                dr["name"] = newname;
                dr["email"] = newemail;
                Sda.Update(Ds);
            }
            catch { };
            operationSucceed = false;
        }
    }
}
